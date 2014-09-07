package com.lenovo.rfc.ui.exec;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.wb.swt.SWTResourceManager;

public class ResultPanel extends Composite {

	/**
	 * Create the composite.
	 *
	 * @param parent
	 * @param style
	 */
	public ResultPanel(Composite parent) {
		super(parent, SWT.BORDER);
		setLayout(new GridLayout(1, false));

		Label lable = new Label(this, SWT.NONE);
		lable.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 12, SWT.NORMAL));
		lable.setText("Finished Test");
		lable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		TreeViewer treeViewer = new ResultTreeViewer(this).getViewer();
		Tree tree = treeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

	}

	@Override
	protected void checkSubclass() {
	}

}
