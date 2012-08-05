package ch.simas.mite4java.data;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

	Integer userId;
	String userName;
	String subDomainName;
	String miteApiKey;
	String selectedFields;

	public String getSelectedFields() {
		return selectedFields;
	}

	public void setSelectedFields(String selectedFields) {
		this.selectedFields = selectedFields;
	}

	public String getMiteApiKey() {
		return miteApiKey;
	}

	public void setMiteApiKey(String miteApiKey) {
		this.miteApiKey = miteApiKey;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubDomainName() {
		return subDomainName;
	}

	public void setSubDomainName(String subDomainName) {
		this.subDomainName = subDomainName;
	}

}
