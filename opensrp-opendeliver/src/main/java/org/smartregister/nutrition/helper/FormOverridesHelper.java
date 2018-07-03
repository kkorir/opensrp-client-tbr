package org.smartregister.nutrition.helper;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.commonregistry.CommonPersonObject;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.form.FieldOverrides;
import org.smartregister.nutrition.application.OpenDeliverApplication;
import org.smartregister.nutrition.util.Constants;
import org.smartregister.util.DateUtil;

import java.util.HashMap;
import java.util.Map;

import util.TbrConstants;

import static org.smartregister.nutrition.repository.ResultsRepository.BASE_ENTITY_ID;
import static org.smartregister.nutrition.util.Utils.getValueFromObs;
import static org.smartregister.util.Utils.getValue;

/**
 * Created by ndegwamartin on 27/01/2018.
 */

public class FormOverridesHelper {

    private String TAG = FormOverridesHelper.class.getCanonicalName();

    private Map<String, String> patientDetails;

    public FormOverridesHelper(Map<String, String> patientDetails) {
        this.patientDetails = patientDetails;
    }

    public void setPatientDetails(Map<String, String> patientDetails) {
        this.patientDetails = patientDetails;
    }

    public Map populateFieldOverrides() {
        Map fields = new HashMap();
        fields.put(TbrConstants.KEY.PARTICIPANT_ID, patientDetails.get(TbrConstants.KEY.PARTICIPANT_ID));
        fields.put(TbrConstants.KEY.FIRST_NAME, patientDetails.get(TbrConstants.KEY.FIRST_NAME));
        fields.put(TbrConstants.KEY.LAST_NAME, patientDetails.get(TbrConstants.KEY.LAST_NAME));
        fields.put(TbrConstants.KEY.PROGRAM_ID, patientDetails.get(TbrConstants.KEY.PROGRAM_ID));
        return fields;
    }

    public FieldOverrides getFieldOverrides() {
        Map fields = populateFieldOverrides();
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getFollowUpFieldOverrides() {
        Map fields = populateFieldOverrides();
        fields.put(TbrConstants.KEY.TREATMENT_INITIATION_DATE, patientDetails.get(TbrConstants.KEY.TREATMENT_INITIATION_DATE));
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getTreatmentFieldOverrides() {
        Map fields = populateFieldOverrides();
        fields.put(TbrConstants.KEY.GENDER, patientDetails.get(TbrConstants.KEY.GENDER));
        String dobString = patientDetails.get(TbrConstants.KEY.DOB);
        String age = "";
        if (StringUtils.isNotBlank(dobString)) {
            try {
                DateTime birthDateTime = new DateTime(dobString);
                String duration = DateUtil.getDuration(birthDateTime);
                if (duration != null) {
                    age = duration.substring(0, duration.length() - 1);
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString(), e);
            }
        }
        fields.put(TbrConstants.KEY.AGE, age);
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getChildFollowupFieldOverrides(CommonPersonObjectClient pc) {
        Map fields = new HashMap();
        try {
            JSONObject json = OpenDeliverApplication.getInstance().getResultsRepository().getLatestVisit(getValue(pc.getColumnmaps(),BASE_ENTITY_ID,false), null);
            String weight = getValueFromObs(json.getJSONArray("obs"),"weight");
            String height = getValueFromObs(json.getJSONArray("obs"),"height");

            fields.put("existing_age", Months.monthsBetween(DateTime.now(), DateTime.parse(patientDetails.get("dob"))).getMonths());
            fields.put("existing_height", height);
            fields.put("existing_weight", weight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject fieldOverridesJson = new JSONObject(fields);
        FieldOverrides fieldOverrides = new FieldOverrides(fieldOverridesJson.toString());
        return fieldOverrides;
    }

    public FieldOverrides getAddContactFieldOverrides() {
        Map fields = new HashMap();
        fields.put(TbrConstants.KEY.PARTICIPANT_ID, patientDetails.get(TbrConstants.KEY.PARTICIPANT_ID));
        fields.put(TbrConstants.KEY.PARENT_ENTITY_ID, patientDetails.get(Constants.KEY._ID));
        return new FieldOverrides(new JSONObject(fields).toString());
    }

    public FieldOverrides getContactScreeningFieldOverrides() {
        Map fields = populateFieldOverrides();
        fields.remove(TbrConstants.KEY.PARTICIPANT_ID);
        return new FieldOverrides(new JSONObject(fields).toString());
    }
}