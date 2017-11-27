package org.smartregister.tbr.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.smartregister.cursoradapter.CursorCommonObjectFilterOption;
import org.smartregister.cursoradapter.CursorCommonObjectSort;
import org.smartregister.cursoradapter.SecuredNativeSmartRegisterCursorAdapterFragment;
import org.smartregister.provider.SmartRegisterClientsProvider;
import org.smartregister.tbr.R;
import org.smartregister.tbr.application.TbrApplication;
import org.smartregister.tbr.helper.view.RenderPatientDemographicCardHelper;
import org.smartregister.tbr.helper.view.RenderPositiveResultsCardHelper;
import org.smartregister.tbr.helper.view.RenderServiceHistoryCardHelper;
import org.smartregister.tbr.servicemode.TbrServiceModeOption;
import org.smartregister.view.activity.SecuredNativeSmartRegisterActivity;
import org.smartregister.view.dialog.DialogOption;
import org.smartregister.view.dialog.FilterOption;
import org.smartregister.view.dialog.ServiceModeOption;
import org.smartregister.view.dialog.SortOption;

import java.util.Map;

import util.TbrConstants;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by samuelgithengi on 11/8/17.
 */

public abstract class BaseRegisterFragment extends SecuredNativeSmartRegisterCursorAdapterFragment {

    @Override
    protected SecuredNativeSmartRegisterActivity.DefaultOptionsProvider getDefaultOptionsProvider() {
        return new SecuredNativeSmartRegisterActivity.DefaultOptionsProvider() {


            @Override
            public ServiceModeOption serviceMode() {
                return new TbrServiceModeOption(null, "Linda Clinic", new int[]{
                        R.string.patient_name, R.string.participant_id, R.string.mobile_phone_number
                }, new int[]{5, 3, 2});
            }

            @Override
            public FilterOption villageFilter() {
                return new CursorCommonObjectFilterOption("no village filter", "");
            }

            @Override
            public SortOption sortOption() {
                return new CursorCommonObjectSort(getResources().getString(R.string.alphabetical_sort), "last_interacted_with desc");
            }

            @Override
            public String nameInShortFormForTitle() {
                return context().getStringResource(R.string.tbreach);
            }
        };
    }

    @Override
    protected SecuredNativeSmartRegisterActivity.NavBarOptionsProvider getNavBarOptionsProvider() {
        return new SecuredNativeSmartRegisterActivity.NavBarOptionsProvider() {

            @Override
            public DialogOption[] filterOptions() {
                return new DialogOption[]{};
            }

            @Override
            public DialogOption[] serviceModeOptions() {
                return new DialogOption[]{
                };
            }

            @Override
            public DialogOption[] sortingOptions() {
                return new DialogOption[]{
                        new CursorCommonObjectSort(getResources().getString(R.string.alphabetical_sort), TbrConstants.KEY.FIRST_NAME),
                        new CursorCommonObjectSort(getResources().getString(R.string.participant_id), TbrConstants.KEY.TBREACH_ID)
                };
            }

            @Override
            public String searchHint() {
                return context().getStringResource(R.string.str_search_hint);
            }
        };
    }


    protected void filter(String filterString, String joinTableString, String mainConditionString) {
        filters = filterString;
        joinTable = joinTableString;
        mainCondition = mainConditionString;
        getSearchCancelView().setVisibility(isEmpty(filterString) ? INVISIBLE : VISIBLE);
        CountExecute();
        filterandSortExecute();
    }

    protected final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(final CharSequence cs, int start, int before, int count) {
            filter(cs.toString(), "", getMainCondition());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    @Override
    protected SmartRegisterClientsProvider clientsProvider() {
        return null;
    }

    @Override
    protected void onInitialization() {//Implement Abstract Method
    }

    @Override
    protected void startRegistration() {//Implement Abstract Method
    }

    @Override
    protected void onCreation() {//Implement Abstract Method
    }

    protected abstract String getMainCondition();

    protected void renderPositiveResultsView(View view, Map<String, String> patientDetails) {
        RenderPositiveResultsCardHelper renderPositiveResultsHelper = new RenderPositiveResultsCardHelper(getActivity(), TbrApplication.getInstance().getResultDetailsRepository());
        renderPositiveResultsHelper.renderView(view.findViewById(R.id.clientPositiveResultsCardView), patientDetails);
    }

    protected void renderDemographicsView(View view, Map<String, String> patientDetails) {

        RenderPatientDemographicCardHelper renderPatientDemographicCardHelper = new RenderPatientDemographicCardHelper(getActivity(), TbrApplication.getInstance().getResultDetailsRepository());
        renderPatientDemographicCardHelper.renderView(view.findViewById(R.id.clientDetailsCardView), patientDetails);

    }

    protected void renderServiceHistoryView(View view, Map<String, String> patientDetails) {
        RenderServiceHistoryCardHelper renderServiceHistoryHelper = new RenderServiceHistoryCardHelper(getActivity(), TbrApplication.getInstance().getResultDetailsRepository());
        renderServiceHistoryHelper.renderView(view.findViewById(R.id.clientServiceHistoryCardView), patientDetails);
    }

}
