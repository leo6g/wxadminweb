package com.lfc.wxadminweb.modules.system.form;


public class RoleUserForm  implements java.io.Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	    //角色ID
		public String roleId;
		//用户ID
        public String userId;
		public String getRoleId() {
			return roleId;
		}
		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}

}