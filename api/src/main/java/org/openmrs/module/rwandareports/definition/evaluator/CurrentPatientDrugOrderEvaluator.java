package org.openmrs.module.rwandareports.definition.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.DrugOrder;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.mohorderentrybridge.api.MoHOrderEntryBridgeService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.evaluator.RowPerPatientDataEvaluator;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rwandareports.definition.CurrentPatientDrugOrder;
import org.openmrs.module.rwandareports.definition.result.CurrentDrugOrderResults;

@Handler(supports={CurrentPatientDrugOrder.class})
public class CurrentPatientDrugOrderEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	StringBuilder result = new StringBuilder();
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		CurrentDrugOrderResults par = new CurrentDrugOrderResults(patientData, context);
		CurrentPatientDrugOrder pd = (CurrentPatientDrugOrder)patientData;
		par.setDrugFilter(pd.getResultFilter());
		List<DrugOrder> orders = Context.getService(MoHOrderEntryBridgeService.class).getDrugOrdersByPatient(pd.getPatient());
		
		for (DrugOrder drugOrder : orders) {
             if((drugOrder != null) && ! drugOrder.isDiscontinuedRightNow()){ 
            	
			         par.setValue(orders);
        	 }
        }
		return par;
	}
}
