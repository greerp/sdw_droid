package com.inqbarna.tablefixheaders.samples.adapters;

import com.inqbarna.tablefixheaders.R;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import com.uk.greer.tablefixheaders.VerticalTextView;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MatrixTableAdapter<T> extends BaseTableAdapter {

	private final static int NAMWIDTH_DIP = 110;
	private final static int WIDTH_DIP = 32;
	private final static int HEIGHT_DIP = 32;
	private final static int HDRHEIGHT_DIP = 100;

	private final Context context;
	private final int hdrHeight;
    private final int namWidth;
	private final LayoutInflater inflater;

	private T[][] table;

	private final int width;
	private final int height;

	public MatrixTableAdapter(Context context) {
		this(context, null);
	}

	public MatrixTableAdapter(Context context, T[][] table) {
		this.context = context;
		Resources r = context.getResources();

		width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_DIP, r.getDisplayMetrics()));
		namWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, NAMWIDTH_DIP, r.getDisplayMetrics()));
		height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DIP, r.getDisplayMetrics()));
		hdrHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HDRHEIGHT_DIP, r.getDisplayMetrics()));

		setInformation(table);

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


	}

	public void setInformation(T[][] table) {
		this.table = table;
	}

	@Override
	public int getRowCount() {
		return table.length - 1;
	}

	@Override
	public int getColumnCount() {
		return table[0].length - 1;
	}

	@Override
	public View getView(int row, int column, View convertView, ViewGroup parent) {

        if (convertView == null) {
            switch (getItemViewType(row, column)) {
                case 1:
                    convertView = inflater.inflate(R.layout.text_view_1, parent, false);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.vertical_text_view, parent, false);
                    break;
                case 3:
                    convertView = inflater.inflate(R.layout.text_view_3, parent, false);
                    break;
                case 4:
                    convertView = inflater.inflate(R.layout.text_view_4, parent, false);
                    break;
            }
        }

		T t = table[row + 1][column + 1];
		if (t==null){
			((TextView) convertView).setText("");
		}
		else{
			((TextView) convertView).setText(t.toString());
		}
		return convertView;
	}

	@Override
	public int getHeight(int row) {
		if ( row==-1)
			return hdrHeight;

		return height;
	}

	@Override
	public int getWidth(int column) {
        if ( column==-1)
		    return namWidth;
        else
            return width;
	}

	@Override
	public int getItemViewType(int row, int column) {

        // View Table Sectons
        // -----
        // |1|2|
        // |4|3|
        // -----

        if ( row==-1 & column==-1)
            return 1;
		else if ( row==-1 && column > -1)
			return 2;
        else if ( row>=0 && column >=0)
			return 3;
        else
            return 4;
	}

	@Override
	public int getViewTypeCount() {
		return 4;
	}
}
