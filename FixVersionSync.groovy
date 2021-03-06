/**
 This script is used to update the version of an project if there is any change in name of the version or release date.
 It will add new version if version does not exist already.
 Set the version as released if date is greaten then past 7 days.
 Target New Project:
 Update the Project Id and Project Key
 **/
 
 @Grab('com.xlson.groovycsv:groovycsv:1.1')
 import static com.xlson.groovycsv.CsvParser.parseCsv
 import com.xlson.groovycsv.PropertyMapper
 import com.atlassian.jira.component.ComponentAccessor;
 import com.atlassian.jira.project.version.VersionManager;
 import com.atlassian.jira.bc.project.version.VersionService
 //import com.atlassian.crowd.embedded.api.CrowdService;
 //import com.atlassian.crowd.embedded.api.User;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import com.atlassian.jira.timezone.TimeZoneManager
 import com.atlassian.jira.project.version.Version;
 
 
 /*Initializing all the variable**/
 List<PropertyMapper> excelBody = new ArrayList<PropertyMapper>()
 List<String> products = new ArrayList<String>();
 List<String> description = new ArrayList<String>();
 List<String> pendingVersion = new ArrayList<String>();
 List<String> pendingVersion1 = new ArrayList<String>();
 List<String> index = new ArrayList<String>();
 List<String> ReleaseDate = new ArrayList<String>();
 List<Version> versionlist = new ArrayList<Version>();
 List<String> updateNameVersion = new ArrayList<String>();
 List<String> productPrefix = new ArrayList<String>();
 def vrsnManager = ComponentAccessor.getVersionManager()
 def versionService = ComponentAccessor.getComponent(VersionService)
 def projectManager = ComponentAccessor.getProjectManager()
 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 def userTimeZone = ComponentAccessor.getComponent(TimeZoneManager).	getLoggedInUserTimeZone()
 def project = projectManager.getProjectObjByKey("PRO")//project key
 long projectid = 13690/*project id*/
 List versions = vrsnManager.getVersions(projectid)/*Reading version from the project*/
 def count = 0
 def directoryURL ="/jira/AhaVersionSync/logs/"
 def filedirectoryURL = "/jira/AhaVersionSync/"
 Map<String, Integer> nameAndCount = new HashMap<>();
 def fileNotFoundCount = 0
 //def productPrefixValue = ["AMN","ANALYTICS","ARCSYS","AUDIT","CASE","DIST","DOCGEN","ECM","EDM","EFM","ESIGN","EVAULT","FORM","HRSD","HRSDIDEAS","INTSVS","KB","MS","PDUI","PPLCARE","PPLCORE","PROCESS","PSF","RPA","ULTIMOBILE","WEBHK"]
 /**Product prefix which is needed to be ignored**/
 def productPrefixValue = ["AMN","ANALYTICS","ARCSYS","AUDIT","CASE","DIST","DOCGEN","EFM","ESIGN","EVAULT","FORM","HRSDIDEAS","KB","MS","PDUI","PPLCARE","PPLCORE","PROCESS","PSF","RPA","WEBHK","QGR","HRSDARCH","JST","JT","ML","PYT","CLO","DAT"] 
 def productPrefixFound = false
 
 /**Getting the user**/
 //CrowdService crowdService = ComponentAccessor.getCrowdService();
 //User user = crowdService.getUser("ulti")
 def userManager = ComponentAccessor.getUserManager();
 def user = userManager.getUserByKey("ulti");
 
 /**Getting the logged in user current time**/
 def userCurrentTime = GregorianCalendar.getInstance(userTimeZone).format("yyyy-MM-dd");//Getting the logged in user current time
 Date now =  format.parse(userCurrentTime)
 
 /***Creating a log file**/
 File file = new File(directoryURL+'log_'+userCurrentTime+".txt");
 file.write("Operation"+"                       "+"Description"+"          "+"Version_Name"+"          "+"Old Value"+"       "+"New Value\n\n")
 /***Creating a log file for errors**/
 File errorfile = new File(directoryURL+'Errorlog_'+userCurrentTime+".txt");
 //errorfile.write("")
 /*Reading csv file*/
try{
        for(line in parseCsv(new FileReader(filedirectoryURL+'AhaReleases_'+userCurrentTime+'.csv'))) {//1. update the path for the CSV File
			 excelBody.add( (PropertyMapper)line)
        }      
} catch(Exception ex) {
	    /*If File is not found creating a BIZ*/
        file.append(" CSV File Not Found")
		errorfile.write("")
		errorfile.append("CSV File Not Found")
		fileNotFoundCount = fileNotFoundCount + 1;
	    
}

 //Iterating through excel
 for(PropertyMapper excelRow : excelBody)
 {
	 products.add(excelRow.Product_prefix+"-"+excelRow.Release_name)//Concating the product and release name.
	 description.add(excelRow.Release_ID)
	 ReleaseDate.add(excelRow.Release_date)
	 productPrefix.add(excelRow.Product_prefix)
 }
 
 //Iterating through the spreadsheet to find duplicate entries
 for(String product : products ){
	 def countProduct = nameAndCount.get(product)
	 if(countProduct == null){
		 nameAndCount.put(product,1);
	 }else{
		 nameAndCount.put(product,++countProduct);
	 }

}



if(excelBody.size()>0 ){
	for(int j=0; j< description.size(); j++){	
		productPrefixFound = false                   
		if (productPrefix[j] in productPrefixValue){//Look for PeopleDoc product prefix. 
			productPrefixFound = true
		}
                count = count + 1
		if(products[j] != null && productPrefixFound == false){//Proceed only if PeopleDoc product is not found. 
			def versionName = products[j].replaceAll('�','-')//Replacing the double hyphen
			def foundcount = 0//Initialising the found count
			def notfoundCount = 0//Initalisinng notfound count
			def vrsnDescription 
			def vrsnName
			def vrsnRelaseDate
			def existingVersion = null
			if(nameAndCount.get(products[j]) <= 1){  
				for(int i = 0 ; i<=versions.size() && foundcount == 0; i++){
				if(versions.getAt(i) !=  null){
					vrsnDescription = versions.getAt(i).getDescription();//Getting version description
					vrsnName = versions.getAt(i).getName();//Getting version name
					vrsnRelaseDate = versions.getAt(i).getReleaseDate();//Getting version releaseDate
					}
					if(vrsnDescription==description[j]){//Comparing the description of the version.
						foundcount = foundcount + 1;
						notfoundCount  = 0;
						if(vrsnName!=versionName){//Comparing if there is no change in version name
							existingVersion = vrsnManager.getVersion(projectid,versionName);//To get the information of existing version to see if there is no version with the same name
							if(existingVersion == null){
							vrsnManager.editVersionDetails(versions.getAt(i), versionName , vrsnDescription)
							file.append("Update_Name"+"                      "+vrsnDescription+"  "+versionName+" "+"'"+vrsnName+"'"+" "+"'"+versionName+"'"+"\n\n")//Adding line to the log file for updateing version Name
							}
							else{
								pendingVersion.add(vrsnDescription);
								file.append("Unable to update Version Name"+"    "+vrsnDescription+"\n\n")

							}	 
						}
						if(ReleaseDate[j] != ""&& vrsnRelaseDate != null){//checking if releaseDate is not empty
							Date versionReleaseDate = format.parse (ReleaseDate[j]);//Getting releaseDate from the sheet.
							if(vrsnRelaseDate!= versionReleaseDate){//Comparing the releaseDate
								vrsnManager.editVersionReleaseDate(versions.getAt(i),versionReleaseDate)
									file.append("Update_Release"+"                   "+vrsnDescription+"  "+versionName+" "+"'"+vrsnRelaseDate+"'"+" "+"'"+versionReleaseDate+"'"+"\n\n")//Adding line to the log file for updating release date.
								
							}
							if(versionReleaseDate-now < -7 && versions.getAt(i).isReleased() == false ){//Getting the difference between version release date from the spreadsheet with the current date
								def result = versionService.validateReleaseVersion(user, versions.getAt(i), versionReleaseDate)//If difference is more then 7 days in past, updating version as released.
								if (result.isValid()) {
											versionService.releaseVersion(result)
								}
								file.append("Set_Released"+"                     "+vrsnDescription+"  "+versionName+"\n\n")//Adding line to the log file for Set_Released.
							}
						}
						else{
							if(vrsnRelaseDate != null){//Clear out the release date only if releasedate exist for the version.
									vrsnManager.editVersionReleaseDate(versions.getAt(i),null)
									file.append("Update_Release"+"                   "+vrsnDescription+"  "+versionName+" "+"'"+vrsnRelaseDate+"'"+" "+"'"+null+"'"+"\n\n")//Adding line to the log file for updating release date.
							}
							
						}
					}
		
				}
			
				
					if(foundcount == 0){//Adding the new version if the description id does not match with any of the existing version.
						existingVersion = null
						if(ReleaseDate[j] != ""){//checking if release date is not empty.
							Date versionReleaseDate = format.parse (ReleaseDate[j]);
							existingVersion = vrsnManager.getVersion(projectid,versionName);//To get the information of existing version to see if there is no version with the same name
							if(versionReleaseDate - now >=1){// After the version is created , checking  the difference between Version release date from spreadsheet and Current date
								if(existingVersion == null){
									def version = vrsnManager.createVersion(versionName, null, versionReleaseDate, description[j], project.id,null, false)
									file.append("Create_Version"+"                   "+description[j]+"  "+versionName+"\n\n")
								}
								else{
									pendingVersion.add(description[j]);
									file.append("Unable to update Version Name"+"    "+description[j]+"\n\n")
								}
							}
							else{
								if(existingVersion == null){
									def version = vrsnManager.createVersion(versionName, null, null, description[j], project.id,null, false)//Creating a new version only if the releaseDate is null.
									file.append("Create_Version"+"                       "+description[j]+"  "+versionName+"\n\n")
								}
								else{
									pendingVersion.add(description[j]);
									file.append("Unable to update Version Name"+"    "+description[j]+"\n\n")
								}

							}
						}
				
					}
				}
			else{
					//errorLogCount = ++errorLogCount
					file.append("Found Duplicate Names in CSV"+"     "+description[j]+"\n\n")
					index.add(description[j])

					
				}
		}
	}
							
}
else{
	if(fileNotFoundCount ==  0){
	 file.append("CSV File is Empty")
	 errorfile.write("")
	 errorfile.append("CSV File is Empty")
	}
}

if(pendingVersion.size()>0 && count == description.size()){
                    file.append("Reprocessing Failures"+"\n\n")
                     processPendingVersion(pendingVersion,description,products,vrsnManager,file,errorfile,projectid,directoryURL);
                     
}
 //Getting the version of the project 
 def gettingVersion(String vrsnName, VersionManager vrsnManager){
     def projectVersion = vrsnManager.getAllVersions()
      def existingVersion = 0
     for(int j =0; j<= projectVersion.size(); j++){
         if(vrsnName == projectVersion.getAt(j).getName()){
             existingVersion = existingVersion + 1
         }

     }
     return existingVersion

 }
 
 def processPendingVersion(List<String> pendingVersion,List<String> description,List<String> products,VersionManager vrsnManager,File file,File errorfile, Long projectid, String directoryURL){
       file.append("Initiating Reprocessing of Failures"+"\n\n")
       def versions = vrsnManager.getVersions(projectid)
       def existingVersion = 0
	   
	   errorfile.write("")
     for(int k=1; k<=pendingVersion.size(); k++){ 
		 def descriptionfound = 0
        for(int j= 0;j<=description.size();j++ ){	
            if(products[j] != null){
             String versionName = products[j].replaceAll('�','-')//Replacing the double hyphen
            	if(description[j]== pendingVersion[pendingVersion.size()-k]){
                    
                	for(int i = 0 ; i<=versions.size(); i++){
                    	if(versions.getAt(i)!= null){
                     		def vrsnDescription = versions.getAt(i).getDescription()
                    		 def vrsnName = versions.getAt(i).getName();//Getting version name
                    		if(vrsnDescription==description[j]){
                        		if(vrsnName!=versionName){
									descriptionfound = descriptionfound+1
                            		existingVersion = vrsnManager.getVersion(10151,versionName);//To get the information of existing version to see if there is no version with the same name
                             
                        			if(existingVersion == null){
                           				 vrsnManager.editVersionDetails(versions.getAt(i), versionName , vrsnDescription)
                            			file.append("Update_Name"+"                      "+vrsnDescription+"  "+versionName+" "+"'"+vrsnName+"'"+" "+"'"+versionName+"'"+"\n\n")//Adding line to the log file for updateing version Name
                            		}
                           		 	else{
										file.append("Not processed"+"                    "+vrsnDescription+"  "+versionName+" "+"'"+vrsnName+"'"+" "+"'"+versionName+"'"+"\n\n")
										//errorfile.write("")
                            			errorfile.append("Operation: Not processed\n"+"Description: "+vrsnDescription+"\n"+"Current Name: "+"'"+vrsnName+"'"+"\n"+"New Name: "+"'"+versionName+" "+"\n\n")//Adding line to the log file for failed version
										//errorLogCount = ++errorLogCount
                   					}

                				}
                    		}
             			}

         			}
					if(descriptionfound == 0){
							file.append("Not processed"+"                    "+description[j]+"  "+versionName+"\n\n")
							//errorfile.write("")
							errorfile.append("Operation: Not Processed - Similar Version Name exist for different Description ID\n"+"Description: "+description[j]+"\n"+"Current Name: "+"'"+versionName+"\n\n")//Adding line to the log file for failed version
							
   					 } 

       			}
          	}
     	}
    }
}


if(index.size >0){
	file.append("Logging duplicate version to Error Log"+"\n\n")
	for(int i = 0;  i<= index.size(); i++  ){
				for(int l = 0; l<= description.size(); l++){
					if(index[i] == description[l]){	
						def versionNameCSV = products[l]
						for(int k =0;k<=products.size();k++){
							if(versionNameCSV == products[k] && description[l]!= description[k]){
								errorfile.write("")
								errorfile.append("Operation: Found Duplicate Names in CSV\n"+"Description: "+index[i]+"\n"+"Version Name: "+"'"+versionNameCSV+"'"+"\n"+"Description: "+description[k]+"\n"+"Version Name: "+"'"+products[k]+"'"+"\n\n")
                                index.remove(description[k])
							}
						}
					}
				}		
			}
}
//This check if error file is updated and if it did, then create the BIZ*/
if(errorfile.length()>0){
	file.append("Creating BIZ in JIRA"+"\n\n")
	def path = filedirectoryURL+'/scripts/BIZ-CreateJIRA.py'
	def proc = path.execute()

}

///Update the release date if date is past 7 days
file.append("checking if Version need to be set to released"+"\n\n")
for(int i = 0 ; i<=versions.size(); i++){
    if(versions.getAt(i) !=  null){
        def vrsnDescription = versions.getAt(i).getDescription();//Getting version description
        def vrsnName = versions.getAt(i).getName();//Getting version name
        def vrsnRelaseDate = versions.getAt(i).getReleaseDate();//Getting version releaseDate
        if(vrsnRelaseDate != null){
            if(vrsnRelaseDate-now < -7 && versions.getAt(i).isReleased() == false ){
                def result = versionService.validateReleaseVersion(user, versions.getAt(i), vrsnRelaseDate)//If difference is more then 7 days in past, updating version as released.
                if (result.isValid()) {
                        versionService.releaseVersion(result)
                }
                file.append("Set_Released"+"                     "+vrsnDescription+"  "+vrsnName+"\n\n")//Adding line to the log file for Set_Released.
            }
        }
   }
}
