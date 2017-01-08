package dev.linhnv.loginregister1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DevLinhnv on 12/29/2016.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    Context context;
    int resource;
    List<Product> list;
    public ProductAdapter(Context context, int resource, List<Product> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewrow = inflater.inflate(R.layout.list_item, parent, false);
        TextView tv_id = (TextView) viewrow.findViewById(R.id.tv_id);
        TextView tv_name = (TextView) viewrow.findViewById(R.id.tv_name);
        TextView tv_price = (TextView) viewrow.findViewById(R.id.tv_price);
        Product getProduct = list.get(position);
        tv_id.setText(getProduct.id +"");
        tv_name.setText(getProduct.name);
        tv_price.setText(getProduct.price+"");
        return viewrow;
    }
}
