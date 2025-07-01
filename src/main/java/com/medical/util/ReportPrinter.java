package com.medical.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for printing reports
 */
public class ReportPrinter implements Printable {
    private DefaultTableModel tableModel;
    private String reportTitle;

    public ReportPrinter(DefaultTableModel tableModel, String reportTitle) {
        this.tableModel = tableModel;
        this.reportTitle = reportTitle;
    }

    public void printReport() {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        
        if (job.printDialog()) {
            try {
                job.print();
                JOptionPane.showMessageDialog(null, "Report printed successfully!");
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(null, "Print failed: " + e.getMessage());
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        Font titleFont = new Font("Arial", Font.BOLD, 16);
        Font headerFont = new Font("Arial", Font.BOLD, 10);
        Font dataFont = new Font("Arial", Font.PLAIN, 9);

        int y = 30;
        
        // Title
        g2d.setFont(titleFont);
        g2d.drawString(reportTitle, 50, y);
        y += 30;
        
        // Date
        g2d.setFont(dataFont);
        g2d.drawString("Generated: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()), 50, y);
        y += 30;

        // Headers
        g2d.setFont(headerFont);
        int x = 50;
        int colWidth = 80;
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            g2d.drawString(tableModel.getColumnName(i), x, y);
            x += colWidth;
        }
        y += 20;

        // Data
        g2d.setFont(dataFont);
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            x = 50;
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                Object value = tableModel.getValueAt(row, col);
                String text = value != null ? value.toString() : "";
                if (text.length() > 12) text = text.substring(0, 12) + "...";
                g2d.drawString(text, x, y);
                x += colWidth;
            }
            y += 15;
            if (y > pf.getImageableHeight() - 50) break;
        }

        return PAGE_EXISTS;
    }
}