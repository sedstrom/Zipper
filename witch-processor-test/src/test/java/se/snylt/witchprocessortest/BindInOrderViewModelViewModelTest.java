package se.snylt.witchprocessortest;


import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;
import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class BindInOrderViewModelViewModelTest {

    @Test
    public void testBindInOrder() {
        BindInOrderViewModel model = new BindInOrderViewModel();
        TestBinderHelper<BindInOrderViewModel, BindInOrderViewModel_ViewHolder> helper
                = new TestBinderHelper<>(
                new BindInOrderViewModel_ViewHolder(),
                new BindInOrderViewModel_ViewBinder().createBinder());

        View viewOne = helper.mockViewForId(R.id.testIdOne, TextView.class);
        View viewTwo = helper.mockViewForId(R.id.testIdTwo, TextView.class);
        View viewThree = helper.mockViewForId(R.id.testIdThree, TextView.class);
        InOrder inOrder = Mockito.inOrder(viewOne, viewTwo, viewThree);
        helper.bind(model);

        inOrder.verify((TextView)helper.getView(R.id.testIdOne)).setText(eq(model.first));
        inOrder.verify((TextView)helper.getView(R.id.testIdTwo)).setText(eq(model.second));
        inOrder.verify((TextView)helper.getView(R.id.testIdThree)).setText(eq(model.third));
    }
}