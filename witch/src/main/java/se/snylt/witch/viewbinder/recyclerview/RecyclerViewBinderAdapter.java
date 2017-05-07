package se.snylt.witch.viewbinder.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import se.snylt.witch.viewbinder.Witch;

/**
 * RecyclerView adapter that uses {@link Binder} for data binding.
 * Each item type should have its own binder.
 * i.e. item of type T should have a Binder<T> provided in constructor.
 *
 * @param <Item> data model for views.
 */
public class RecyclerViewBinderAdapter<Item> extends RecyclerView.Adapter<EmptyViewHolder> {

    private List<Item> items;

    private final List<Binder<? extends Item>> binders;

    @SafeVarargs
    public RecyclerViewBinderAdapter(List<Item> items, Binder<? extends Item> ...binders) {
        this.items = items;
        this.binders = Arrays.asList(binders);
    }

    @SafeVarargs
    public RecyclerViewBinderAdapter(Binder<? extends Item> ...binders) {
        this(null, binders);
    }

    @Override
    public int getItemViewType(int position) {
        return getBinder(items.get(position)).getLayoutId();
    }

    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new EmptyViewHolder(inflater.inflate(viewType, viewGroup, false));
    }

    private Binder<? extends Item> getBinder(Item item) {
        for(Binder<? extends Item> binder: binders) {
            if(binder.getItemType() == item.getClass()) {
                return binder;
            }
        }
        String itemType = item.getClass().getSimpleName();
        throw new IllegalArgumentException("No binder for found for " + itemType + ". Make sure binder for " + itemType
                + " is provided to adapter");
    }

    @Override
    public void onBindViewHolder(EmptyViewHolder emptyViewHolder, int position) {
        Item item = items.get(position);
        Witch.bind(getBinder(item).take(item), emptyViewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * Base class for RecyclerView binder.
     * @param <Item> item to be bound to view.
     */
    public static abstract class Binder<Item> {

        /**
         * Item to be bound.
         */
        protected Item item;

        /**
         * Layout resource id for view creation.
         */
        private final int layoutId;

        protected Binder(int layoutId) {
            this.layoutId = layoutId;
        }

        /**
         * Updates item to be bound.
         * @param item item to be bound
         * @return Binder ready to bind {@param item}
         */
        Binder take(Object item) {
            this.item = (Item) item;
            return this;
        }

        int getLayoutId() {
            return layoutId;
        }

        public abstract Class getItemType();
    }
}
