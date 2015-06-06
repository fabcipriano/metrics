def sqlresult = """
declare
  workflowName VARCHAR2(200)   := '${ctx.wfname}';
  workflowPSName VARCHAR2(200) := '${ctx.wfname}_Presave';
  workflowPLName VARCHAR2(200) := '${ctx.wfname}_Pipeline';
  workflowId NUMBER;
  workflowPLId NUMBER;
  workflowPSId NUMBER;
  workflowLoadReturn VARCHAR2(200);
  screenId number;

begin

""";

ctx.screens.each {
    sqlresult += "select algarcrm.tl_screen_sq.nextval into screenId from dual;\n"
    sqlresult += "Insert into ALGARCRM.Tl_Screen(id, name, description, url, tl_screen_type_id, tl_plugin_id) values(screenId, ${it.name},${it.description},${it.description}, ${it.tl_screen_type_id}, ${it.tl_plugin_id});\n"
    sqlresult += "Insert into ALGARCRM.TL_WORKFLOW_TRANSITION (ID,NAME,DISPLAY_NAME,ACTION_ID,TL_WORKFLOW_ID,TL_SCREEN_ID,POSITION) values (algarcrm.tl_workflow_transition_sq.nextval,${it.action_name},${it.action_displayname},${it.action_id},workflowId,screenId,${it.position});\n\n"
}


return sqlresult + "\n\nend;";
