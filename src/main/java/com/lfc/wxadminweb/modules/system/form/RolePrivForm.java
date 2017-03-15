package com.lfc.wxadminweb.modules.system.form;


public class RolePrivForm  implements java.io.Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   //角色ID
		public String roleId;
		//权限ID
        public String privId;
        //类型 1是菜单 2是按钮
        public Integer type;

		public String getPrivId() {
			return privId;
		}
		public void setPrivId(String privId) {
			this.privId = privId;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

}