package org.mltk.mahout.util.load.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.mltk.mahout.util.load.LoadTasteDataModel;

public class LoadMemTasteDataImpl implements LoadTasteDataModel {

	private Map<Long, Object> recDataMap;

	public LoadMemTasteDataImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoadMemTasteDataImpl(Map<Long, Object> recDataMap) {
		super();
		this.recDataMap = recDataMap;
	}

	public DataModel loadDataFromOutRes() {

		FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();

		for (Entry<Long, Object> entryUser : recDataMap.entrySet()) {
			PreferenceArray prefsForUser = new GenericUserPreferenceArray(10);
			prefsForUser.setUserID(0, entryUser.getKey());

			Map<Integer, Object> userRecData = (Map<Integer, Object>) entryUser
					.getValue();
			for (Entry<Integer, Object> entryRec : userRecData.entrySet()) {
				int itemId = entryRec.getKey();

				Map<String, Object> rec = (Map<String, Object>) entryRec
						.getValue();
				prefsForUser.setItemID(itemId, (Long) rec.get("itemId"));
				prefsForUser.setValue(itemId, (Float) rec.get("value"));
			}

			preferences.put(entryUser.getKey(), prefsForUser);
		}

		DataModel model = new GenericDataModel(preferences);

		return model;
	}

	public Map<Long, Object> getRecDataMap() {
		return recDataMap;
	}

	public void setRecDataMap(Map<Long, Object> recDataMap) {
		this.recDataMap = recDataMap;
	}

}
