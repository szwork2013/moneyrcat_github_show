package com.emperises.monercat.interfacesandevents;

import com.emperises.monercat.domain.model.ZcmUser;

public interface EditMyInfoInterface {

	void onMyInfoChange(ZcmUser info);
	void onAgeEditAfter(String age);
	void onNickNameEditAfter(String nickNmae);
	void onGenderEditAfter(String gender);
	void onAddressEditAfter(String address);
}
