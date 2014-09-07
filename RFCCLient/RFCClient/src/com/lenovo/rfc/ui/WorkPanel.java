package com.lenovo.rfc.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;

import com.lenovo.rfc.ui.exec.ResultPanel;
import com.lenovo.rfc.ui.exec.TestPanel;

public class WorkPanel {
	private SashForm sashForm;

	public SashForm getViewer() {
		return sashForm;
	}

	public WorkPanel(Composite parent) {
		this.sashForm = new SashForm(parent, SWT.BORDER);
		new TestPanel(sashForm);
		new ResultPanel(sashForm);

		sashForm.setWeights(new int[] { 1, 1 });
	}
}
