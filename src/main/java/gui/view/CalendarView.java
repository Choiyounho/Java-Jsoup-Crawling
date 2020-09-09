package gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static utils.CommonsConstant.BLANK;
import static utils.CommonsConstant.HYPHEN;

public class CalendarView extends JFrame {

    private static final String SUNDAY = "일";
    private static final String MONDAY = "월";
    private static final String TUESDAY = "화";
    private static final String WEDNESDAY = "수";
    private static final String THURSDAY = "목";
    private static final String FRIDAY = "금";
    private static final String SATURDAY = "토";
    private static final String YEAR = "년";
    private static final String MONTH = "월";
    private static final String DATE = "날짜 : ";
    private static final int DIVISION_VALUE = 2;

    private static final int GRID_LAYOUT_ROWS = 7;
    private static final int GRID_LATOUT_COLS = 7;
    private static final int GRID_LAYOUT_HORIZONTAL_GAP = 5;
    private static final int GRID_LAYOUT_VERTICAL_GAP = 5;
    private static final GridLayout grid = new GridLayout(GRID_LAYOUT_ROWS, GRID_LATOUT_COLS, GRID_LAYOUT_HORIZONTAL_GAP, GRID_LAYOUT_VERTICAL_GAP);//행,열,수평갭,수직갭

    private static final int LAST_YEAR = 2020;
    private static final int FIRST_YEAR = 2000;
    private static final int GUI_WIDTH = 900;
    private static final int GUI_HEIGHT = 600;

    private static final int JTEXT_AREA_RWOS = 60;
    private static final int JTEXT_AREA_COLUMNS = 40;

    private static final String JSCROLLPANE_LOCATION_NORTH = "North";
    private static final String SCROLLPANE_LOCATION_CENTER = "Center";
    private static final String JSCROLLPANE_LOCATION_CENTER1 = SCROLLPANE_LOCATION_CENTER;
    private static final String JSCROLLPANE_LOCATION_EAST = "East";

    private Choice choiceYear;
    private Choice choiceMonth;

    private JLabel yearJLabel;
    private JLabel monthJLabel;
    private Calendar calendar = Calendar.getInstance();

    private  JLabel[] dayLabel = new JLabel[7];
    private  JPanel selectPanel = new JPanel();

    private String[] day = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    private JButton[] days = new JButton[42];

    public CalendarView() {
        setTitle(DATE + calendar.get(Calendar.YEAR) + HYPHEN + (calendar.get(Calendar.MONTH) + 1) + HYPHEN + calendar.get(Calendar.DATE));
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension1 = this.getSize();
        int xPosition = (int) (dimension.getWidth() / DIVISION_VALUE - dimension1.getWidth() / DIVISION_VALUE);
        int yPosition = (int) (dimension.getHeight() / DIVISION_VALUE - dimension1.getHeight() / DIVISION_VALUE);

        setLocation(xPosition, yPosition);
        setResizable(false);
        setVisible(true);

        choiceYear = new Choice();
        choiceMonth = new Choice();
        yearJLabel = new JLabel(YEAR);
        monthJLabel = new JLabel(MONTH);

        init();
    }

    private void init() {
        select();
        calendar();
    }

    public Color getBackgroundColor() {
        return this.getBackground();
    }

    private void select() {
        JPanel panel = new JPanel(grid);//7행 7열의 그리드레이아웃

        for (int i = LAST_YEAR; i >= FIRST_YEAR; i--) {
            choiceYear.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            choiceMonth.add(String.valueOf(i));
        }
        for (int i = 0; i < day.length; i++) {//요일 이름을 레이블에 출력
            dayLabel[i] = new JLabel(day[i], JLabel.CENTER);
            panel.add(dayLabel[i]);
        }

        dayLabel[6].setForeground(Color.BLUE);//토요일 색상
        dayLabel[0].setForeground(Color.RED);//일요일 색상


        selectPanel.add(choiceYear);
        selectPanel.add(yearJLabel);
        selectPanel.add(choiceMonth);
        selectPanel.add(monthJLabel);

        JTextArea area = new JTextArea(JTEXT_AREA_RWOS, JTEXT_AREA_COLUMNS);
        area.setCaretPosition(area.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(area);
        this.add(selectPanel, JSCROLLPANE_LOCATION_NORTH); //연도와 월을 선택할 수 있는 화면읠 상단에 출력
        this.add(panel, JSCROLLPANE_LOCATION_CENTER1);
        this.add(scrollPane, JSCROLLPANE_LOCATION_EAST);

        String m = (calendar.get(Calendar.MONTH) + 1) + BLANK;
        String y = calendar.get(Calendar.YEAR) + BLANK;
        choiceYear.select(y);
        choiceMonth.select(m);
        CrawlingActionListener actionListener = new CrawlingActionListener(area, choiceYear, choiceMonth);
        for (int i = 0; i < 42; i++) {//TODO : 달력은 42개의 날짜 고정이라 상수화 X
            days[i] = new JButton(BLANK); //제목이 없는 버튼 생성
            if (i % 7 == 0)
                days[i].setForeground(Color.RED); //일요일 버튼의 색
            else if (i % 7 == 6)
                days[i].setForeground(Color.BLUE); //토요일 버튼의 색
            else
                days[i].setForeground(Color.BLACK);
            days[i].addActionListener(actionListener);
            panel.add(days[i]);
        }
        ItemListener itemListener = new CrawlingItemListener(days);
        choiceYear.addItemListener(itemListener);
        choiceMonth.addItemListener(itemListener);
    }


    public void calendar() {
        int year = Integer.parseInt(choiceYear.getSelectedItem());
        int month = Integer.parseInt(choiceMonth.getSelectedItem());
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month - 1, 1);
        int max = gregorianCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);//해당 달의 최대 일 수 획득
        int week = gregorianCalendar.get(Calendar.DAY_OF_WEEK);//해당 달의 시작 요일

        String today = Integer.toString(calendar.get(Calendar.DATE));//오늘 날짜 획득
        String today_month = Integer.toString(calendar.get(Calendar.MONTH) + 1);//오늘의 달 획득

        for (int i = 0; i < days.length; i++) {
            days[i].setEnabled(true);
        }
        for (int i = 0; i < week - 1; i++) {//시작일 이전의 버튼을 비활성화
            days[i].setEnabled(false);
        }
        for (int i = week; i < max + week; i++) {
            days[i - 1].setText((String.valueOf(i - week + 1)));
            days[i - 1].setBackground(Color.WHITE);
            if (today_month.equals(String.valueOf(month))) {//오늘이 속한 달과 같은 달인 경우
                if (today.equals(days[i - 1].getText())) {//버튼의 날짜와 오늘날짜가 일치하는 경우
                    days[i - 1].setBackground(Color.CYAN);//버튼의 배경색 지정
                }
            }
        }
        for (int i = (max + week - 1); i < days.length; i++) {//날짜가 없는 버튼을 비활성화
            days[i].setEnabled(false);
        }
    }

}
