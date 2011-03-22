package de.cr.freitonal.client.widgets.piecelist;

import java.util.ArrayList;

import com.google.gwt.event.shared.EventBus;

import de.cr.freitonal.client.rpc.PieceSearchMask;
import de.cr.freitonal.shared.models.Piece;
import de.cr.freitonal.shared.models.PieceList;
import de.cr.freitonal.shared.models.PieceSkeleton;

public class PieceListPresenter {
	@SuppressWarnings("unused")
	private final EventBus eventBus;
	private final View view;

	public interface View {
		void setPieceList(ArrayList<Piece> pieceList);
	}

	public PieceListPresenter(EventBus eventBus, View pieceListView) {
		this.eventBus = eventBus;
		this.view = pieceListView;
	}

	public void setPieceList(PieceList pieceList, PieceSearchMask pieceSearchMask) {
		view.setPieceList(convertPieceSkeletons(pieceList, pieceSearchMask));
	}

	private ArrayList<Piece> convertPieceSkeletons(PieceList pieceSkeletonList, PieceSearchMask pieceSearchMask) {
		ArrayList<Piece> pieceList = new ArrayList<Piece>();
		if (pieceSkeletonList == null) {
			return pieceList;
		}

		PieceSkeletonConverter converter = new PieceSkeletonConverter(pieceSearchMask);

		for (PieceSkeleton pieceSkeleton : pieceSkeletonList) {
			Piece piece = converter.convertPieceSkeleton(pieceSkeleton);
			pieceList.add(piece);
		}

		return pieceList;
	}

}
