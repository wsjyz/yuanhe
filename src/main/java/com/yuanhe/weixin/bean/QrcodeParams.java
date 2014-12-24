package com.yuanhe.weixin.bean;

/**
 * Created by dam on 2014/12/23.
 */
public class QrcodeParams {

    public String action_name;
    public ActionInfo action_info;

    public String getAction_name() {
        return action_name;
    }

    public void setAction_name(String action_name) {
        this.action_name = action_name;
    }

    public ActionInfo getAction_info() {
        return action_info;
    }

    public void setAction_info(ActionInfo action_info) {
        this.action_info = action_info;
    }

    public class ActionInfo{

        private Scene scene;

        public Scene getScene() {
            return scene;
        }

        public void setScene(Scene scene) {
            this.scene = scene;
        }

        public class Scene{
            public String scene_id;

            public String getScene_id() {
                return scene_id;
            }

            public void setScene_id(String scene_id) {
                this.scene_id = scene_id;
            }
        }
    }
}
