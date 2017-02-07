package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.viewbinder.viewfinder.ActivityViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewViewFinder;

public class Zipper {

    final static String TAG = "Zipper";

    public final static int VIEW_HOLDER_TAG = Integer.MIN_VALUE;

    private final static HashMap<Object, WeakReference<Binding>> bindings = new HashMap<>();

    public static UnBinder bind(Object target, Activity activity) {
        return bind(target, new ActivityViewFinder(activity, activity));
    }

    public static UnBinder bind(Object target, Activity view, Object user) {
        return bind(target, new ActivityViewFinder(view, user));
    }

    public static UnBinder bind(Object target, View view) {
        return bind(target, new ViewViewFinder(view, view));
    }

    public static UnBinder bind(Object target, View view, Object user) {
        return bind(target, new ViewViewFinder(view, user));
    }

    /**
     * Bind target values to views in provided view finder
     * @param target view model with bindings defined
     * @param viewFinder view finder containing views defined in target.
     * @return complete binding
     */
    private static UnBinder bind(Object target, ViewFinder viewFinder) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        Binding binding = getOrCreateBinding(target);
        for (ViewBinder viewBinder : binding.getViewBinders()) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }
        return binding.registersUser(viewFinder.getUser());
    }

    /**
     * Get binding for provided target view model. Creates new binding if not already existing.
     * @param target annotated view model
     * @return binding for target
     */
    private static Binding getOrCreateBinding(Object target) {
        final Class key = target.getClass();
        Binding binding;
        if (!isBindingCreated(key)) {
            bindings.put(key, new WeakReference((binding = createBinding(target))));
        } else {
            binding = bindings.get(key).get();
        }
        return binding;
    }

    private static boolean isBindingCreated(Class key) {
        return bindings.containsKey(key) && bindings.get(key).get() != null;
    }

    /**
     * Get view holder for provided target. Will be created if not created already.
     * @param target annotated view model
     * @param viewFinder view finder handling the view holder
     * @return view holder for target
     */
    private static Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object viewHolder = viewFinder.getViewHolder();
        if (viewHolder == null) {
            viewHolder = createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

    /**
     * Creates a binding from annotated target view model
     * @param target the view model
     * @return binding
     */
    private static Binding createBinding(Object target) {
        try {
            Class clazz = ClassUtils.findBinding(target);
            return ((BindingCreator) clazz.newInstance()).createBinding();
        } catch (Exception e) {
            throw new BindingNotFoundException(target);
        }
    }

    /**
     * Creates a view holder from annotated target view model
     * @param target the view model
     * @return view holder for views defined in target
     */
    private static Object createViewHolder(Object target) {
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

}
