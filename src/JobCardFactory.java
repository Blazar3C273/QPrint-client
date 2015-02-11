import com.sun.istack.internal.NotNull;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public abstract class JobCardFactory {

    public static final String NULL_FIELD_TEXT = "---";
    private static final String NO_DATA_STRING = "Нет данных.";
    private static final String COMPLETED_TEXT = "Зкаказ исполнен";
    public static final String PAID_DOBUTTON_TEXT = "Исполнить заказ";
    public static final String PACE_COUNT_TEXT = "Кол-во страниц заказа";
    public static final String JOB_STATE_TEXT = "Состояние заказа";
    public static final String CONFORM_CODE_TEXT = "Код подтверждения";
    public static final String COST_TEXT = "Цена";
    public static final String CUSTOM_MASSAGE_TEXT = "Клиент не оставил комментария.";
    public static final String DO_BUTTON_TEXT = "Действие не доступно";
    public static final String CUSTOM_MASSAGE_BORDER_TEXT = "Комментарий клиента";
    public static final String JOB_IN_PROGRESS_TEXT = "В обработке";

    @NotNull
    public static JPanel getJobCard(final Job inJob, String inCardDiscription) {
        String cardBorderTitle = inCardDiscription;

        JPanel countField;
        JPanel panel;
        JLabel jobStateLabe;
        JTextField jobStateField;
        JPanel pane2;
        JLabel conformingCodeLabel;
        JTextField conformingCodeField;
        JPanel pane3;
        JLabel pageCountLabel;
        JTextField pageCountField;
        JPanel pane4;
        JLabel costLabel;
        JTextField costField;
        JPanel pane5;
        JScrollPane scrollPane;
        JTextArea customMessageTextArea;
        JPanel pane6;
        JButton doButton;

        countField = new JPanel();
        panel = new JPanel();
        jobStateLabe = new JLabel();
        jobStateField = new JTextField();
        pane2 = new JPanel();
        conformingCodeLabel = new JLabel();
        conformingCodeField = new JTextField();
        pane3 = new JPanel();
        pageCountLabel = new JLabel();
        pageCountField = new JTextField();
        pane4 = new JPanel();
        costLabel = new JLabel();
        costField = new JTextField();
        pane5 = new JPanel();
        scrollPane = new JScrollPane();
        customMessageTextArea = new JTextArea();
        pane6 = new JPanel();
        doButton = new JButton();

        {
            countField.setBorder(new TitledBorder(null, cardBorderTitle, TitledBorder.LEFT, TitledBorder.TOP));
            countField.setAlignmentX(0.4F);
            countField.setLayout(new BoxLayout(countField, BoxLayout.PAGE_AXIS));

            //======== panel4 ========
            {
                panel.setBorder(new LineBorder(Color.gray, 1, true));
                panel.setLayout(new GridLayout());

                //---- label2 ----
                jobStateLabe.setText(JOB_STATE_TEXT);
                jobStateLabe.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(jobStateLabe);

                //---- jobStateFieldLast ----
                jobStateField.setText(NULL_FIELD_TEXT);
                jobStateField.setFont(jobStateField.getFont().deriveFont(jobStateField.getFont().getSize() + 5f));
                jobStateField.setHorizontalAlignment(SwingConstants.CENTER);
                jobStateField.setMinimumSize(new Dimension(6, 6));
                jobStateField.setEditable(false);
                jobStateField.setBackground(Color.white);
                panel.add(jobStateField);
            }
            countField.add(panel);

            //======== panel5 ========
            {
                pane2.setBorder(new LineBorder(Color.gray, 1, true));
                pane2.setLayout(new GridLayout());

                //---- label3 ----
                conformingCodeLabel.setText(CONFORM_CODE_TEXT);
                conformingCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
                pane2.add(conformingCodeLabel);

                //---- codeFieldLast ----
                conformingCodeField.setFont(conformingCodeField.getFont().deriveFont(conformingCodeField.getFont().getSize() + 5f));
                conformingCodeField.setHorizontalAlignment(SwingConstants.CENTER);
                conformingCodeField.setText(NULL_FIELD_TEXT);
                conformingCodeField.setEditable(false);
                conformingCodeField.setBackground(Color.white);
                pane2.add(conformingCodeField);
            }
            countField.add(pane2);

            //======== panel6 ========
            {
                pane3.setBorder(new LineBorder(Color.gray, 1, true));
                pane3.setLayout(new GridLayout());

                //---- label4 ----
                pageCountLabel.setText(PACE_COUNT_TEXT);
                pageCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
                pane3.add(pageCountLabel);

                //---- pageNumberFieldLast ----
                pageCountField.setText(NULL_FIELD_TEXT);
                pageCountField.setFont(pageCountField.getFont().deriveFont(pageCountField.getFont().getSize() + 5f));
                pageCountField.setHorizontalAlignment(SwingConstants.CENTER);
                pageCountField.setEditable(false);
                pageCountField.setBackground(Color.white);
                pane3.add(pageCountField);
            }
            countField.add(pane3);

            //======== panel7 ========
            {
                pane4.setBorder(new LineBorder(Color.gray, 1, true));
                pane4.setLayout(new GridLayout());

                //---- label5 ----
                costLabel.setText(COST_TEXT);
                costLabel.setHorizontalAlignment(SwingConstants.CENTER);
                pane4.add(costLabel);

                //---- costFieldLast ----
                costField.setText(NULL_FIELD_TEXT);
                costField.setHorizontalAlignment(SwingConstants.CENTER);
                costField.setFont(costField.getFont().deriveFont(costField.getFont().getSize() + 5f));
                costField.setEditable(false);
                costField.setBackground(Color.white);
                pane4.add(costField);
            }
            countField.add(pane4);

            //======== panel8 ========
            {
                pane5.setBorder(new TitledBorder(CUSTOM_MASSAGE_BORDER_TEXT));
                pane5.setLayout(new BoxLayout(pane5, BoxLayout.X_AXIS));

                //======== scrollPane1 ========
                {

                    //---- textArea1 ----
                    customMessageTextArea.setText(CUSTOM_MASSAGE_TEXT);
                    customMessageTextArea.setFont(customMessageTextArea.getFont().deriveFont(customMessageTextArea.getFont().getSize() + 4f));
                    customMessageTextArea.setRows(4);
                    customMessageTextArea.setLineWrap(true);
                    scrollPane.setViewportView(customMessageTextArea);
                }
                pane5.add(scrollPane);
            }
            countField.add(pane5);

            //======== panel2 ========
            {
                pane6.setBorder(new LineBorder(Color.gray, 1, true));
                pane6.setLayout(new GridLayout());

                //---- button2 ----
                doButton.setText(DO_BUTTON_TEXT);
                doButton.setFont(doButton.getFont().deriveFont(doButton.getFont().getSize() + 3f));
                doButton.addActionListener(new ActionListener() {
                    JobCardStateEnum jobCardState = JobCardStateEnum.whaitingForPrinting;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch (jobCardState) {

                            case whaitingForConformation: {
                                inJob.setJobState(JobState.COMPLETED);
                                Server.updateJobState(inJob);
                                jobCardState = JobCardStateEnum.notActive;
                                if (e != null) {
                                    ((JButton) e.getSource()).setEnabled(false);
                                }

                                MainWindow.mainLoopThread.interrupt();
                                break;
                            }
                            case whaitingForPrinting: {
                                inJob.startPrintingJob();
                                if (e != null) {
                                    ((JButton) e.getSource()).setText("Подтвердите выполнение заказа");
                                }
                                jobCardState = JobCardStateEnum.whaitingForConformation;
                                break;
                            }
                        }

                    }
                });
                pane6.add(doButton);
            }
            countField.add(pane6);
        }

        if (inJob != null) {
            switch (inJob.getJobState()) {
                case COMPLETED:
                    jobStateField.setText(COMPLETED_TEXT);
                    doButton.setText(DO_BUTTON_TEXT);
                    doButton.setEnabled(false);
                    break;
                case PAID:
                    jobStateField.setText(JOB_IN_PROGRESS_TEXT);
                    doButton.setText(PAID_DOBUTTON_TEXT);
            }
            //jobStateField.setText(inJob.getJobState().name());
            conformingCodeField.setText(inJob.getOrder_code());
            pageCountField.setText(String.valueOf(inJob.getPage_count()));
            costField.setText(String.valueOf(inJob.getPrice()));
            customMessageTextArea.setText(inJob.getMassage());
        } else {
            doButton.setEnabled(false);
            jobStateField.setText(NO_DATA_STRING);
            conformingCodeField.setText(NO_DATA_STRING);
            pageCountField.setText(NO_DATA_STRING);
            costField.setText(NO_DATA_STRING);
            customMessageTextArea.setText(NO_DATA_STRING);
        }

        return countField;

    }
}
