package com.su.funycard.logic;

import java.util.ArrayList;
import java.util.List;

import com.su.funycard.uiwidget.PicLayer;

public class PicLayerRestoreCache {
	List<PicLayer> picLayers = new ArrayList<PicLayer>();

	public void pushPicLayer(PicLayer picLayer) {
		if (picLayers.size() < 5) {
			picLayers.add(picLayer);
		} else {
			picLayers.remove(0);
			picLayers.add(picLayer);
		}

	}

	public PicLayer popPicLayer() {
		if (picLayers.size() != 0) {

			int last = picLayers.size() - 1;
			PicLayer picLayer = picLayers.get(last);
			picLayers.remove(last);
			return picLayer;
		}
		return null;

	}

	public void clear() {
		picLayers = new ArrayList<PicLayer>();
	}
}
