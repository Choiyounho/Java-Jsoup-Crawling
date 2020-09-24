package gui.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.IntStream;

import static utils.CommonsConstant.*;

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
    private static final int GRID_LAYOUT_COLUMNS = 7;
    private static final int GRID_LAYOUT_HORIZONTAL_GAP = 5;
    private static final int GRID_LAYOUT_VERTICAL_GAP = 5;
    private static final GridLayout grid = new GridLayout(GRID_LAYOUT_ROWS, GRID_LAYOUT_COLUMNS, GRID_LAYOUT_HORIZONTAL_GAP, GRID_LAYOUT_VERTICAL_GAP); //행,열,수평갭,수직갭

    private static final int LAST_YEAR = 2020;
    private static final int FIRST_YEAR = 2000;
    private static final int GUI_WIDTH = 900;
    private static final int GUI_HEIGHT = 600;

    private static final int JTEXT_AREA_ROWS = 60;
    private static final int JTEXT_AREA_COLUMNS = 40;
    private static final int JBUTTON_TOTAL_COLUMNS_AND_ROWS = 42;

    private static final String JSCROLLPANE_LOCATION_NORTH = "North";
    private static final String SCROLLPANE_LOCATION_CENTER = "Center";
    private static final String JSCROLLPANE_LOCATION_CENTER1 = SCROLLPANE_LOCATION_CENTER;
    private static final String JSCROLLPANE_LOCATION_EAST = "East";
    public static final int INCREASE_MONTHS = 1;
    public static final int JANUARY = 1;
    public static final int DECEMBER = 12;
    public static final int INDEX_SATURDAY = 6;
    public static final int INDEX_SUNDAY = 0;

    private Choice choiceYear;
    private Choice choiceMonth;

    private final JLabel yearJLabel;
    private final JLabel monthJLabel;
    private final Calendar calendar = Calendar.getInstance();

    private final JLabel[] dayLabel = new JLabel[GRID_LAYOUT_COLUMNS];
    private JPanel selectPanel = new JPanel();

    private final String[] day = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    private final JButton[] days = new JButton[JBUTTON_TOTAL_COLUMNS_AND_ROWS];

    public CalendarView() {
        setTitle(printFrameTitle());
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO : layout(Right, Left)Frame 어떤 역할인지 확인하고, 이름 맞게 변경하기
        Dimension layoutRightFrame = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension layoutLeftFrame = this.getSize();
        int xPosition = (int) ((layoutRightFrame.getWidth() / DIVISION_VALUE) - (layoutLeftFrame.getWidth() / DIVISION_VALUE));
        int yPosition = (int) ((layoutRightFrame.getHeight() / DIVISION_VALUE) - (layoutLeftFrame.getHeight() / DIVISION_VALUE));

        setLocation(xPosition, yPosition);
        setResizable(false);
        setVisible(true);

        choiceYear = new Choice();
        choiceMonth = new Choice();
        yearJLabel = new JLabel(YEAR);
        monthJLabel = new JLabel(MONTH);

        init();
    }

    private String printFrameTitle() {
        return new StringBuilder()
                .append(DATE)
                .append(calendar.get(Calendar.YEAR))
                .append(HYPHEN)
                .append(calendar.get(Calendar.MONTH) + INCREASE_MONTHS)
                .append(HYPHEN)
                .append(calendar.get(Calendar.DATE))
                .toString();
    }

    private void init() {
        createCalendarFrame();
        calendar();
    }

    public Color getBackgroundColor() {
        return this.getBackground();
    }

    private void createCalendarFrame() {
        JPanel calendarFrame = new JPanel(grid);//7행 7열의 그리드레이아웃

        choiceYear = initChoiceYear();
        choiceMonth = initChoiceMonth();
        initCalendarFrame(calendarFrame);

        dayLabel[INDEX_SATURDAY].setForeground(Color.BLUE);//토요일 색상
        dayLabel[INDEX_SUNDAY].setForeground(Color.RED);//일요일 색상

        // TODO : selectPanel 이 어떤 역할인지 확인해보고 변수 명 바꾸기
        initSelectPanel(selectPanel);

        JTextArea area = new JTextArea(JTEXT_AREA_ROWS, JTEXT_AREA_COLUMNS);
        area.setCaretPosition(area.getDocument().getLength());

        JScrollPane scrollPane = new JScrollPane(area);
        this.add(selectPanel, JSCROLLPANE_LOCATION_NORTH); //연도와 월을 선택할 수 있는 화면읠 상단에 출력
        this.add(calendarFrame, JSCROLLPANE_LOCATION_CENTER1);
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
            calendarFrame.add(days[i]);
        }
        ItemListener itemListener = new CrawlingItemListener(days);
        choiceYear.addItemListener(itemListener);
        choiceMonth.addItemListener(itemListener);
    }

    private JPanel initSelectPanel(JPanel selectPanel) {
        selectPanel.add(choiceYear);
        selectPanel.add(yearJLabel);
        selectPanel.add(choiceMonth);
        selectPanel.add(monthJLabel);
        return selectPanel;
    }

    private void initCalendarFrame(JPanel calendarFrame) {
        IntStream.range(INDEX_SUNDAY, day.length).forEach(index -> {
            dayLabel[index] = new JLabel(day[index], JLabel.CENTER);
            calendarFrame.add(dayLabel[index]);
        });
    }

    private Choice initChoiceMonth() {
        for (int i = JANUARY; i <= DECEMBER; i++) {
            choiceMonth.add(String.valueOf(i));
        }
        return choiceMonth;
    }

    private Choice initChoiceYear() {
        for (int i = LAST_YEAR; i >= FIRST_YEAR; i--) {
            choiceYear.add(String.valueOf(i));
        }
        return choiceYear;
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
