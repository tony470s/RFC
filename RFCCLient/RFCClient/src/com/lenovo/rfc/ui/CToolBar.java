package com.lenovo.rfc.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import com.lenovo.rfc.utils.test.ITestManager.ITestListener;
import com.lenovo.rfc.utils.test.TestManager;

public class CToolBar {
	private ToolBar toolBar;
	private ToolItem item_start_test;

	public ToolBar getViewer() {
		return toolBar;
	}

	public CToolBar(Composite parent) {
		toolBar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
		toolBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				ToolItem toolItem = toolBar.getItem(new Point(e.x, e.y));
				if (toolItem == item_start_test) {
					TestManager.instance().start();
				}
			}
		});
		item_start_test = new CToolItem(toolBar);
	}

	public class CToolItem extends ToolItem implements ITestListener {
		private String icon_start = "res\\start_test.png";
		private String icon_end = "res\\test_in_progress.png";

		public CToolItem(ToolBar parent) {
			super(parent, SWT.NONE);
			setImage(SWTResourceManager.getImage(icon_start));
			TestManager.instance().addITestListener(this);

		}

		@Override
		public void onTestStart() {
			getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					setImage(SWTResourceManager.getImage(icon_end));
					setEnabled(false);
					setText("Test in progress...");
				}
			});

		}

		@Override
		public void onTestEnd() {
			getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					setImage(SWTResourceManager.getImage(icon_start));
					setEnabled(true);
					setText("Test Finished");
				}
			});

		}

		@Override
		protected void checkSubclass() {
			return;
		}
	}
}
