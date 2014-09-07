package com.lenovo.rfc.ui.exec;

import java.util.List;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.lenovo.rfc.utils.test.ITestManager.OnTestSuiteUpdateListener;
import com.lenovo.rfc.utils.test.TestManager;
import com.lenovo.rfc.utils.test.data.Test;
import com.lenovo.rfc.utils.test.data.TestSuite;

public class TestTreeViewer implements OnTestSuiteUpdateListener {
	private TreeViewer treeViewer;
	private Tree tree;
	private Display display;
	private TestManager testManager;
	Font bigFont = new Font(display, "Arial Unicode MS", 13, SWT.NONE);
	Font mediFont = new Font(display, "Arial Unicode MS", 13, SWT.NONE);

	Font smallFont = new Font(display, "Arial Unicode MS", 11, SWT.NONE);

	public TestTreeViewer(Composite parent) {
		testManager = TestManager.instance();
		testManager.addOnTestSuiteUpdateListener(this);
		this.treeViewer = new TreeViewer(parent, SWT.VIRTUAL);
		treeViewer.addTreeListener(new ITreeViewerListener() {

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				packColumns((TreeViewer) event.getSource());
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				packColumns((TreeViewer) event.getSource());
			}

			private void packColumns(final TreeViewer treeViewer) {
				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						TreeColumn[] treeColumns = treeViewer.getTree()
								.getColumns();
						for (TreeColumn treeColumn : treeColumns) {
							treeColumn.pack();
						}
					}
				});
			}

		});
		this.tree = this.treeViewer.getTree();
		tree.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TreeItem treeItem = (TreeItem) event.item;
				if (treeItem.getData() instanceof TestSuite) {
					event.height = event.gc.getFontMetrics().getHeight() + 22;

				} else if (treeItem.getData() instanceof Test) {
					event.height = event.gc.getFontMetrics().getHeight() + 20;
				} else if (treeItem.getData() instanceof String) {
					event.height = event.gc.getFontMetrics().getHeight() + 18;
				}
			}
		});
		this.display = this.tree.getDisplay();
		buildContent();
		initData();
	}

	private void initData() {
		treeViewer.setContentProvider(new ITreeContentProvider() {
			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {

			}

			@Override
			public void dispose() {

			}

			@Override
			public boolean hasChildren(Object element) {
				return getChildren(element).length != 0;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof TestSuite) {
					TestSuite testSuite = (TestSuite) inputElement;
					if (testSuite.getTestList().size() != 0) {
						return testSuite.getTestList().toArray();
					}
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof Test) {
					Test test = (Test) parentElement;
					if (test.getInfoSet().size() != 0) {
						return test.getInfoSet().entrySet().toArray();
					}
				} else if (parentElement instanceof Entry<?, ?>) {
					Entry<String, List<String>> enry = (Entry<String, List<String>>) parentElement;
					return enry.getValue().toArray();
				}
				return new Object[0];
			}
		});
	}

	private void buildContent() {
		TreeViewerColumn tvc = new TreeViewerColumn(treeViewer, SWT.NONE);
		TreeColumn tc = tvc.getColumn();
		tc.setWidth(400);
		tvc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public Color getForeground(Object element) {
				if (element instanceof TestSuite) {
					return display.getSystemColor(SWT.COLOR_BLUE);
				} else if (element instanceof Test) {
					Test test = (Test) element;
					switch (test.getResult()) {
					case 0:
						return super.getForeground(element);
					case 1:
						return display.getSystemColor(SWT.COLOR_RED);
					case 2:
						return display.getSystemColor(SWT.COLOR_DARK_GREEN);

					default:
						break;
					}
				}
				return super.getForeground(element);
			}

			@Override
			public Font getFont(Object element) {
				if (element instanceof Test) {
					return bigFont;
				} else if (element instanceof Entry<?, ?>) {
					return mediFont;
				} else if (element instanceof String) {
					return smallFont;
				} else {
					return super.getFont(element);
				}
			};

			@Override
			public String getText(Object element) {
				if (element instanceof Test) {
					Test test = (Test) element;
					return test.getSpec();
				} else if (element instanceof Entry<?, ?>) {
					Entry<String, List<String>> entry = (Entry<String, List<String>>) element;
					return entry.getKey();
				} else if (element instanceof String) {
					return element.toString();
				} else {
					return null;
				}
			}
		});

	}

	public TreeViewer getViewer() {
		return treeViewer;
	}

	@Override
	public void onTestSuiteUpdate() {
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				treeViewer.refresh();
			}
		});
	}

	@Override
	public void onTestSuiteInit() {

	}
}
