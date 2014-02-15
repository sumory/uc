package com.sumory.uc.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件读取
 * 
 * @author sumory.wu
 * @date 2012-2-29 下午12:40:02
 */
public class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    private static final String CONFIGFILE = "uc_config.xml";// 配置文件名
    private static Map<String, String> idGeneratorConfig = new HashMap<String, String>();// idGenerator配置
    private static Map<String, Object> passportConfig = new HashMap<String, Object>();// passport配置
    private static boolean ready = false;// 读配置文件的状态

    public ConfigUtils() {
        loadConfig();
    }

    static synchronized void loadConfig() {
        try {
            SAXReader reader = new SAXReader();
            Document doc = null;
            try {
                InputStream in = ConfigUtils.class.getClassLoader().getResourceAsStream(CONFIGFILE);
                doc = reader.read(in);
                in.close();
            }
            catch (Exception e) {
                logger.error("can't not read uc_config.xml", e);
            }

            Element uc = doc.getRootElement();

            idGeneratorConfig.put("default-user-worker-id", uc.elementText("default-user-worker-id"));
            idGeneratorConfig.put("default-group-worker-id", uc.elementText("default-group-worker-id"));
            idGeneratorConfig.put("default-feed-worker-id", uc.elementText("default-feed-worker-id"));
            idGeneratorConfig.put("default-comment-worker-id", uc.elementText("default-comment-worker-id"));
            idGeneratorConfig.put("default-app-worker-id", uc.elementText("default-app-worker-id"));
            idGeneratorConfig.put("default-store-worker-id", uc.elementText("default-store-worker-id"));
            idGeneratorConfig.put("default-broadcast-worker-id", uc.elementText("default-broadcast-worker-id"));
            idGeneratorConfig.put("default-contacttag-worker-id", uc.elementText("default-contacttag-worker-id"));
            idGeneratorConfig.put("default-filetag-worker-id", uc.elementText("default-filetag-worker-id"));
            idGeneratorConfig.put("default-msg-worker-id", uc.elementText("default-msg-worker-id"));
            idGeneratorConfig.put("default-calendar-worker-id", uc.elementText("default-calendar-worker-id"));
            idGeneratorConfig.put("default-assignment-worker-id", uc.elementText("default-assignment-worker-id"));
            idGeneratorConfig.put("default-answer-worker-id", uc.elementText("default-answer-worker-id"));
            idGeneratorConfig.put("default-notice-worker-id", uc.elementText("default-notice-worker-id"));
            idGeneratorConfig.put("default-judgement-worker-id", uc.elementText("default-judgement-worker-id"));

            passportConfig.put("username-expiry", uc.elementText("username-expiry"));
            passportConfig.put("email-expiry", uc.elementText("email-expiry"));
            passportConfig.put("username-prefix", uc.elementText("username-prefix"));// mc中存储<username,userid>，用于key的前缀
            passportConfig.put("email-prefix", uc.elementText("email-prefix"));// mc中存储<email,userid>，用于key的前缀

            List<Element> mc_nodeList = uc.element("mc").elements("mc-node");
            String[] mc_nodes = new String[mc_nodeList.size()];
            for (int i = 0; i < mc_nodeList.size(); i++) {
                Element node = mc_nodeList.get(i);
                if (!"".equals(node.getText())) {
                    mc_nodes[i] = node.getText();
                }
            }
            passportConfig.put("mc-nodes", mc_nodes);

            ready = true;

        }
        catch (Exception e) {
            logger.error("can't not read uc_config.xml. init uc failed", e);
        }
    }

    public static Map<String, String> getIdGeneratorConfig() {
        if (!ready)
            loadConfig();
        return idGeneratorConfig;
    }

    public static Map<String, Object> getPassportConfig() {
        if (!ready)
            loadConfig();
        return passportConfig;
    }

    public static void main(String[] args) {
        Map<String, String> idConfig = ConfigUtils.getIdGeneratorConfig();
        Map<String, Object> passportConfig = ConfigUtils.getPassportConfig();

        Iterator<Map.Entry<String, String>> idIt = idConfig.entrySet().iterator();
        while (idIt.hasNext()) {
            Map.Entry<String, String> entry = idIt.next();
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        Iterator<Map.Entry<String, Object>> ppIt = passportConfig.entrySet().iterator();
        while (ppIt.hasNext()) {
            Map.Entry<String, Object> entry = ppIt.next();
            if (entry.getKey().equals("mc-nodes")) {
                String[] nodes = ((String[]) entry.getValue());
                for (int i = 0; i < nodes.length; i++) {
                    System.out.println(entry.getKey() + ":" + nodes[i]);
                }
            }
            else
                System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
