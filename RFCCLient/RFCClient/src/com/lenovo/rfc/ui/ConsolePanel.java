package com.lenovo.rfc.ui;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

import com.lenovo.rfc.utils.ConsoleManager;
import com.lenovo.rfc.utils.ConsoleManager.ConsoleInfo;
import com.lenovo.rfc.utils.IConsoleManager.OnDataUpdateListener;

public class ConsolePanel extends Composite {

	/**
	 * Create the composite.
	 *
	 * @param parent
	 * @param style
	 */
	public ConsolePanel(Composite parent) {
		super(parent, SWT.BORDER);
		setLayout(new GridLayout(1, false));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		Label label = new Label(composite, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Î¢ÈíÑÅºÚ", 10, SWT.NORMAL));
		label.setText("Output");
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false));
		Button btn_clearAll = new Button(composite, SWT.NONE);
		btn_clearAll.setText("ClearALL");
		btn_clearAll.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,
				false));
		btn_clearAll.addMouseListener(btn_clearAllAdapter);
		listViewer = new ConsoleListViewer(this).getViewer();
		listViewer.getList().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private MouseAdapter btn_clearAllAdapter = new MouseAdapter() {
		@Override
		public void mouseUp(MouseEvent e) {
			ConsoleManager.instance().clearAll();
		};
	};
	private ListViewer listViewer;

	@Override
	protected void checkSubclass() {
	}

	private static class ConsoleListViewer implements OnDataUpdateListener {
		private ListViewer listViewer;
		private List list;
		private Display display;

		public ConsoleListViewer(Composite parent) {
			listViewer = new ListViewer(parent);
			ConsoleManager.instance().addOnDataUpdateListener(this);
			this.list = listViewer.getList();
			this.display = list.getDisplay();
			buildContents();
			listViewer.setInput(ConsoleManager.instance().getConsoleInfos());
		}

		public ListViewer getViewer() {
			return listViewer;
		}

		private void buildContents() {
			listViewer.setContentProvider(new ArrayContentProvider() {
				@Override
				public Object[] getElements(Object inputElement) {
					return super.getElements(inputElement);
				}
			});

			listViewer.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					if (element instanceof ConsoleInfo) {
						ConsoleInfo info = (ConsoleInfo) element;
						return info.getContent();
					}
					return null;
				}

				@Override
				public Color getForeground(Object element) {
					if (element instanceof ConsoleInfo) {
						ConsoleInfo info = (ConsoleInfo) element;
						if (info.getType().equals(ConsoleInfo.type_error)) {
							return display.getSystemColor(SWT.COLOR_RED);
						}
					}
					return super.getForeground(element);
				}
			});
		}

		@Override
		public void onDataUpdate() {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					listViewer.refresh();
				}
			});
		}
	}
}
