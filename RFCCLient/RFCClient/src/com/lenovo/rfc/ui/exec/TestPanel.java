package com.lenovo.rfc.ui.exec;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.lenovo.rfc.utils.test.ITestManager.OnTestSuiteUpdateListener;
import com.lenovo.rfc.utils.test.TestManager;
import com.lenovo.rfc.utils.test.data.TestSuite;

public class TestPanel extends Composite implements OnTestSuiteUpdateListener {
	private TestTreeViewer testTreeViewer;
	private Display display;
	private Label label;
	private TestManager testManager;

	/**
	 * Create the composite.
	 *
	 * @param parent
	 * @param style
	 */
	public TestPanel(Composite parent) {
		super(parent, SWT.BORDER);
		setLayout(new GridLayout(1, false));
		testManager = TestManager.instance();
		testManager.addOnTestSuiteUpdateListener(this);
		label = new Label(this, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 12, SWT.NORMAL));
		label.setText("Current Test: ");

		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		testTreeViewer = new TestTreeViewer(this);
		testTreeViewer
				.getViewer()
				.getTree()
				.setLayoutData(
						new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		display = getDisplay();
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void onTestSuiteUpdate() {

	}

	@Override
	public void onTestSuiteInit() {
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				String labelText = "Current Test: ";
				if (testManager.getTestSuite().size() == 0) {
					return;
				}
				TestSuite testSuite = testManager.getTestSuite().getLast();
				labelText = labelText + testSuite.getLocal();
				label.setText(labelText);
				testTreeViewer.getViewer().setInput(testSuite);
				testTreeViewer.getViewer().refresh();
			}
		});
	}
}
