package com.adriano.simplelistviewsnackbar;


import android.content.*;
import java.util.*;
import android.widget.*;
import android.view.*;

public class CustomAdapter extends BaseAdapter {  

	Context context; 
	LayoutInflater inflater;
	
	ArrayList<Lista> itemList;
	List<Lista> listaList = null;
	
	public CustomAdapter(Context ctx, List<Lista> listaList) { 
	//public CustomAdapter(Context context, ArrayList<Lista> modelList) {  
		this.context = ctx; 
		this.listaList = listaList;
		//this.itemList = itemList;
        inflater = LayoutInflater.from(context);
		this.itemList = new ArrayList<Lista>();
		this.itemList.addAll(listaList);
	}  
	
	@Override  
	public int getCount() {  
		return listaList.size();  
	} 
	
	@Override  
	public Object getItem(int position)	{  
		return listaList.get(position);  
	}  
	
	@Override  
	public long getItemId(int position)	{  
		return position;  
	}  
	
	private class ViewHolder{
        TextView tvNome, txtId, txtData, txtHora;
		ImageView img_planeta;
    }
	
	@Override  
	public View getView(final int position, View convertView, ViewGroup parent) {  
		ViewHolder holder = null;
		convertView = null; 
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
			//convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
			holder = new ViewHolder();		
			holder.tvNome = convertView.findViewById(R.id.tv_nome);  
			holder.img_planeta = convertView.findViewById(R.id.iv_imagem);  
			convertView.setTag(holder);
			
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
			Lista m = itemList.get(position);  
			holder.tvNome.setText(m.getPlaneta());
			holder.img_planeta.setImageResource(m.getImagem());
		
		return convertView;  
	}  
	
	public void filter(String newText) {
		newText = newText.toLowerCase(Locale.getDefault());
		listaList.clear();
		if (newText.length() == 0) {
			listaList.addAll(itemList);
		} else {
			for (Lista l : itemList) {
				if (l.getPlaneta().toLowerCase(Locale
											   .getDefault()).contains(newText)) {
					listaList.add(l);
				}
			}
		}
		notifyDataSetChanged();
    }
}
