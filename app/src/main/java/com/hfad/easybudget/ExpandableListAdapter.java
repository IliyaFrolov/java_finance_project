package com.hfad.easybudget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private List<String> expandableListTitle = new ArrayList<>();
    private HashMap<String, List<String>> expandableListDetail = new HashMap<>();
    private List<String> childrenTitles = new ArrayList<>();
    private Context context;

    public void initExpandableListAdapter(String strDate, List<Integer> numericInput, List<String> childrenTitles
    , Context context) {
        this.childrenTitles = childrenTitles;
        this.context = context;
        add(strDate, numericInput);
    }

    public void addEntry(String strDate, List<Integer> numericInput) {
        add(strDate, numericInput);
    }

    private void add(String strDate, List<Integer> numericInput) {
        expandableListTitle.add(strDate);
        expandableListDetail.put(strDate, IntStream.range(0, childrenTitles.size())
        .mapToObj(i -> new String(childrenTitles.get(i)+numericInput.get(i)))
        .collect(Collectors.toList()));
    }

    @Override
    public int getGroupCount() {
        return expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return expandableListDetail.get(expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return expandableListTitle.get(listPosition);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return expandableListDetail.get(expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.entry_list_group, null);
        }
        TextView listTitleTextView = convertView.findViewById(R.id.list_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        String listItem = (String) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.entry_list_item, null);
        }
        TextView expandedListTextView = convertView.findViewById(R.id.list_item);
        expandedListTextView.setText(listItem);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return false;
    }
}
