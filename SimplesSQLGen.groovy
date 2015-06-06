
screen1 = new HashMap()
screen1.put("name", 'Encaminhar');
screen1.put("description", 'Solicitação de Serviço');
screen1.put("url", '/service-request/postpaid-contestation-complaint/screens/forward');
screen1.put("description", 'Solicitação de Serviço');
screen1.put("tl_screen_type_id", '1');
screen1.put("tl_plugin_id", '21');
screen1.put("action_name", 'Encaminhar');
screen1.put("action_displayname", 'Encaminhar');
screen1.put("action_id", '23');
screen1.put("position", '2');


screen2 = new HashMap();
screen2.put("name", 'Clonar');
screen2.put("description", 'Clonar de Solicitação de Serviço');
screen2.put("url", '/service-request/postpaid-contestation-complaint/screens/clone');
screen2.put("description", 'Clone DNA da Solicitação de Serviço');
screen2.put("tl_screen_type_id", '2');
screen2.put("tl_plugin_id", '666');
screen2.put("action_name", 'Clonar');
screen2.put("action_displayname", 'Clonar');
screen2.put("action_id", '66');
screen2.put("position", '1');

def context = new HashMap();

context.put("screens", [screen1, screen2]);
context.put("wfname", 'SS_Reclamacao_Contestacao_Pos_Pago_Algar_Telecom');

println Eval.me("ctx", context, new File('TemplateSQL.groovy').text).toString();

