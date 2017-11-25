package shop.ineed.app.ineed.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;

import shop.ineed.app.ineed.R;
import shop.ineed.app.ineed.activity.DetailsProductsActivity;
import shop.ineed.app.ineed.domain.Product;
import shop.ineed.app.ineed.util.HighlightRenderer;
import shop.ineed.app.ineed.util.ImageUtils;

/**
 * Created by jose on 10/27/17.
 *
 * ArrayAdapter para gerenciar os elementos da pesquisa de produtos.
 *
 */

public class ProductSearchAdapter extends ArrayAdapter<Product> {

    private Context context;
    private HighlightRenderer highlightRenderer;

    public ProductSearchAdapter(Context context, int resource){
        super(context, resource);
        this.context = context;
        this.highlightRenderer = new HighlightRenderer(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewGroup cell = (ViewGroup) convertView;

        if(cell == null){
            cell = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.adapter_cell_search_product, parent, false);
        }

        // Init Views
        TextView txtName = (TextView) cell.findViewById(R.id.txtNameProductSearch);
        TextView txtDescription = (TextView) cell.findViewById(R.id.txtDescriptionProductSearch);
        TextView txtPriceProductSearch = (TextView) cell.findViewById(R.id.txtPriceProductSearch);
        ImageView ivProductSearch = (ImageView) cell.findViewById(R.id.ivProductSearch);

        // Result
        Product result = getItem(position);

        // Set values
        txtName.setText(highlightRenderer.renderHighlights(result.getName()));
        txtDescription.setText(result.getDescription());
        txtPriceProductSearch.setText("R$ " + result.getPrice());
        ImageUtils.displayImageFromUrl(context, result.getPictures().get(0), ivProductSearch);

        // OnClick -> Chamada para activity de detalhe do produto
        cell.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsProductsActivity.class);
            intent.putExtra("product", result);
            context.startActivity(intent);
        });

        return cell;
    }

    @Override
    public void addAll(@NonNull Collection<? extends Product> collection) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(collection);
        } else {
            for (Product item : collection) {
                add(item);
            }
        }
    }
}
