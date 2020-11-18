<script type="text/javascript">
jQuery(document).ready(function() {
  var removeIssueType = function() {
    jQuery(".aui-list-item-link").filter(function(){ 
	  return jQuery(this).text() === "Account Request" || jQuery(this).text() === "Customization Patch Request" 
	}).parent().hide();
  }
  var findIssueType = function() {
    jQuery("span.drop-menu").click(removeIssueType);
    jQuery("#issuetype-field").keyup(removeIssueType);
    jQuery("#issuetype-field").click(function() {
      setTimeout(removeIssueType, 1);
    });
    jQuery("#project").change(function() {
      setTimeout(findIssueType, 1000);
    });
  }
  JIRA.bind(JIRA.Events.NEW_CONTENT_ADDED, function(e,context) {
    findIssueType();
  });
  setTimeout(findIssueType, 500);
});
</script>