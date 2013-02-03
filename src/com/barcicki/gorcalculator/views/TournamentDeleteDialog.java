package com.barcicki.gorcalculator.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.barcicki.gorcalculator.R;
import com.barcicki.gorcalculator.database.TournamentModel;

public class TournamentDeleteDialog extends AlertDialog {

	private TournamentModel mTournament;
	
	public TournamentDeleteDialog(Context context) {
		super(context);

		setTitle(context.getString(R.string.tournament_delete));
		setMessage(context.getString(R.string.tournament_delete_message));
		setCancelable(false);
		
		setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.yes), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				TournamentModel.delete(TournamentModel.class, mTournament.getId());
				dismiss();
			}
		});
		setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(android.R.string.no), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		});
		setIcon(android.R.drawable.ic_dialog_alert);
	}

	public TournamentModel getTournament() {
		return mTournament;
	}

	public void setTournament(TournamentModel tournament) {
		this.mTournament = tournament;
	}
	
	

}
