package com.su.funycard.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.su.funycardpro.R;

import com.su.funycard.util.HttpUtil.MLog;

public class PlayerRegisterFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_pregister, null);
		Button btn_back_regeister = (Button) view
				.findViewById(R.id.btn_back_regeister);

		btn_back_regeister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction()
						.remove(PlayerRegisterFragment.this).commit();

			}
		});
		return view;
	}
}
