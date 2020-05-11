package es.centroafuera.rolappeame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

class PartidaAdapterExpandible extends BaseExpandableListAdapter {
    Context context;
    List<String> grupoExpandible;
    HashMap<String, List<String>> itemExpandible;

    public PartidaAdapterExpandible(Context context, List<String> grupoExpandible, HashMap<String, List<String>> itemExpandible){
        this.context = context;
        this.grupoExpandible = grupoExpandible;
        this.itemExpandible = itemExpandible;
    }

    @Override
    public int getGroupCount() {
        return grupoExpandible.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return itemExpandible.get(grupoExpandible.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return grupoExpandible.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemExpandible.get(grupoExpandible.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String grupo = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.grupo_expandible, null);
        }

        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        tvNombre.setText(grupo);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String child = (String) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_expandible, null);
        }

        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        tvNombre.setText(child);
        CheckBox cbIncluido = convertView.findViewById(R.id.cbIncluido);
        cbIncluido.setChecked(true); //FIXME: No sé mantener el checkbox

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
