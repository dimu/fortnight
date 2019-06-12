package swt;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class HelloWorldComposite extends Composite {
	private Text text;
	private Text text_1;
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public HelloWorldComposite(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(176, 196, 222));
		setLayout(new FormLayout());
		
		Label label = new Label(this, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(224, 255, 255));
		FormData fd_label = new FormData();
		fd_label.right = new FormAttachment(0, 90);
		fd_label.top = new FormAttachment(0, 29);
		fd_label.left = new FormAttachment(0, 29);
		label.setLayoutData(fd_label);
		label.setText("\u8D77\u98DE\u65E5\u671F");
		
		DateTime dateTime = new DateTime(this, SWT.BORDER);
		FormData fd_dateTime = new FormData();
		fd_dateTime.top = new FormAttachment(0, 22);
		fd_dateTime.left = new FormAttachment(0, 96);
		dateTime.setLayoutData(fd_dateTime);
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(224, 255, 255));
		FormData fd_label_1 = new FormData();
		fd_label_1.right = new FormAttachment(0, 285);
		fd_label_1.top = new FormAttachment(0, 29);
		fd_label_1.left = new FormAttachment(0, 224);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u5230\u8FBE\u65E5\u671F");
		
		DateTime dateTime_1 = new DateTime(this, SWT.BORDER);
		FormData fd_dateTime_1 = new FormData();
		fd_dateTime_1.top = new FormAttachment(0, 22);
		fd_dateTime_1.left = new FormAttachment(0, 291);
		dateTime_1.setLayoutData(fd_dateTime_1);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(224, 255, 255));
		FormData fd_label_2 = new FormData();
		fd_label_2.right = new FormAttachment(0, 90);
		fd_label_2.top = new FormAttachment(0, 55);
		fd_label_2.left = new FormAttachment(0, 29);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u51FA\u53D1\u57CE\u5E02");
		
		text = new Text(this, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(dateTime, 0, SWT.RIGHT);
		fd_text.top = new FormAttachment(0, 52);
		fd_text.left = new FormAttachment(0, 96);
		text.setLayoutData(fd_text);
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBackground(SWTResourceManager.getColor(224, 255, 255));
		FormData fd_label_3 = new FormData();
		fd_label_3.right = new FormAttachment(0, 285);
		fd_label_3.top = new FormAttachment(0, 52);
		fd_label_3.left = new FormAttachment(0, 224);
		label_3.setLayoutData(fd_label_3);
		label_3.setText("\u5230\u8FBE\u57CE\u5E02");
		
		text_1 = new Text(this, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.right = new FormAttachment(dateTime_1, 0, SWT.RIGHT);
		fd_text_1.top = new FormAttachment(0, 52);
		fd_text_1.left = new FormAttachment(0, 291);
		text_1.setLayoutData(fd_text_1);
		
		Button button = new Button(this, SWT.NONE);
		FormData fd_button = new FormData();
		fd_button.top = new FormAttachment(text, 6);
		fd_button.left = new FormAttachment(0, 95);
		fd_button.right = new FormAttachment(0, 144);
		button.setLayoutData(fd_button);
		button.setText("\u67E5\u8BE2");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(29, 131, 64, 64);
		FormData fd_composite = new FormData();
		fd_composite.top = new FormAttachment(0, 131);
		fd_composite.left = new FormAttachment(0, 29);
		composite.setLayoutData(fd_composite);
		TableColumnLayout tcl_composite = new TableColumnLayout();
		composite.setLayout(tcl_composite);
		
		TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setBackground(SWTResourceManager.getColor(230, 230, 250));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn, new ColumnPixelData(80, true, true));
		tblclmnNewColumn.setText("\u8D77\u98DE\u57CE\u5E02");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn_1, new ColumnPixelData(80, true, true));
		tblclmnNewColumn_1.setText("\u5230\u8FBE\u57CE\u5E02");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn_2, new ColumnPixelData(125, true, true));
		tblclmnNewColumn_2.setText("\u8D77\u98DE\u65F6\u95F4");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tcl_composite.setColumnData(tblclmnNewColumn_3, new ColumnPixelData(125, true, true));
		tblclmnNewColumn_3.setText("\u5230\u8FBE\u65F6\u95F4");
		
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText(new String[] {"CTU", "PEK", "2013-12-23 14:25", "2013-12-23 16:12"});
		
		TableItem tableItem_1 = new TableItem(table, SWT.NONE);
		tableItem_1.setText(new String[] {"CKG", "CAN", "2013-12-23 15:23", "2013-12-23 17:15"});
		
		TableItem tableItem_2 = new TableItem(table, SWT.NONE);
		tableItem_2.setText(new String[] {"WUH", "PVG", "2013-12-23 16:12", "2013-12-23 18:00"});
		
		TableItem tableItem_3 = new TableItem(table, SWT.NONE);
		tableItem_3.setText(new String[] {"TSA", "NKG", "2013-12-23 17:00", "2013-12-23 20:12"});
		
		Button button_1 = new Button(this, SWT.NONE);
		FormData fd_button_1 = new FormData();
		fd_button_1.right = new FormAttachment(button, 64, SWT.RIGHT);
		fd_button_1.top = new FormAttachment(text, 6);
		fd_button_1.left = new FormAttachment(button, 15);
		button_1.setLayoutData(fd_button_1);
		button_1.setText("\u5BFC\u51FA");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
