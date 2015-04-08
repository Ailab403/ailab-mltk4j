package org.mltk.mahout.load.impl;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.mltk.mahout.load.LoadTasteDataModel;

public class LoadFileTasteDataImpl implements LoadTasteDataModel {

	private String modelFilePath;

	public LoadFileTasteDataImpl(String modelFilePath) {
		super();
		this.modelFilePath = modelFilePath;
	}

	public DataModel loadDataFromOutRes() {

		try {
			DataModel model = new FileDataModel(new File(this.modelFilePath));

			return model;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}
	}

	public String getModelFilePath() {
		return modelFilePath;
	}

	public void setModelFilePath(String modelFilePath) {
		this.modelFilePath = modelFilePath;
	}

}
