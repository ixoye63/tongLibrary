package com.tongtong.tonglib.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tongtong.tonglib.R;


public class DemoListAdapter extends ArrayAdapter<DemoListItem> {

    private static class ViewHolder {
        TextView title;
        TextView description;
    }

    public DemoListAdapter(Context context, DemoListItem[] demos) {
        super(context, R.layout.tonglib_list_item_view, demos);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public DemoListItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DemoListItem demo = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.tonglib_list_item_view, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tonglib_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.tonglib_description);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(demo.mTitle);
        viewHolder.description.setText(demo.mDescription);

        return convertView;
    }
}
