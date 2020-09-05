package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import static utils.CommonsConstant.*;

public class GuiCrawlingApi extends JFrame implements ActionListener, ItemListener {
    public static final String JSOUP_CRAWLING_GUI_API = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BodyMatter?qt_ty=QT1&Base_de=";

    public static final int GRID_LAYOUT_ROWS = 7;
    public static final int GRID_LATOUT_COLS = 7;
    public static final int GRID_LAYOUT_HORIZONTAL_GAP = 5;
    public static final int GRID_LAYOUT_VERTICAL_GAP = 5;
    public static final String SUNDAY = "일";
    public static final String MONDAY = "월";
    public static final String TUESDAY = "화";
    public static final String WEDNESDAY = "수";
    public static final String TURSDAY = "목";
    public static final String FRIDAY = "금";
    public static final String SATURDAY = "토";
    public static final int GUI_WIDTH = 900;
    public static final int GUI_HEIGHT = 600;
    public static final String YEAR = "년";
    public static final String MONTH = "월";
    public static final String BLANK = "";
    public static final int DIVISION_VALUE = 2;
    public static final String HASHTAG_BIBLE_INFO_BOX = "#bibleinfo_box";
    public static final String HASHTAG_DAILY_BIBLE_INFO = "#dailybible_info";
    public static final String DATE = "날짜 : ";
    public static final String BAR = "-";
    public static final int LAST_YEAR = 2020;
    public static final int FIRST_YEAR = 2000;
    public static final int JTEXT_AREA_RWOS = 60;
    public static final int JTEXT_AREA_COLUMNS = 40;
    public static final String JSCROLLPANE_LOCATION_NORTH = "North";
    public static final String SCROLLPANE_LOCATION_CENTER = "Center";
    public static final String JSCROLLPANE_LOCATION_CENTER1 = SCROLLPANE_LOCATION_CENTER;
    public static final String JSCROLLPANE_LOCATION_EAST = "East";

    private Choice choiceYear;
    private Choice choiceMonth;
    private JLabel yearJLabel;
    private JLabel monthJLabel;
    private JTextArea area;
    private GregorianCalendar gregorianCalendar;
    private int year;
    private int month;
    private JLabel[] dayLabel = new JLabel[7];
    private String[] day = {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, TURSDAY, FRIDAY, SATURDAY};
    private JButton[] days = new JButton[42];//7일이 6주이므로 42개의 버튼필요
    private JPanel selectPanel = new JPanel();
    private GridLayout grid = new GridLayout(GRID_LAYOUT_ROWS, GRID_LATOUT_COLS, GRID_LAYOUT_HORIZONTAL_GAP, GRID_LAYOUT_VERTICAL_GAP);//행,열,수평갭,수직갭
    private Calendar calendar = Calendar.getInstance();
    private Dimension dimension;
    private Dimension dimension1;
    private int xPosition;
    private int yPosition;


    public GuiCrawlingApi() { //TODO : domain 패키지로 옮기고 접근제어자 넣기

        setTitle(DATE + calendar.get(Calendar.YEAR) + BAR + (calendar.get(Calendar.MONTH) + 1) + BAR + calendar.get(Calendar.DATE));
        setSize(GUI_WIDTH, GUI_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        dimension1 = this.getSize();
        xPosition = (int) (dimension.getWidth() / DIVISION_VALUE - dimension1.getWidth() / DIVISION_VALUE);
        yPosition = (int) (dimension.getHeight() / DIVISION_VALUE - dimension1.getHeight() / DIVISION_VALUE);

        setLocation(xPosition, yPosition);//화면의 가운데에 출력
        setResizable(false);
        setVisible(true);

        choiceYear = new Choice();
        choiceMonth = new Choice();
        yearJLabel = new JLabel(YEAR);
        monthJLabel = new JLabel(MONTH);

        init();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        area.setText(BLANK);
        String year = choiceYear.getSelectedItem();
        String month = choiceMonth.getSelectedItem();
        JButton jButton = (JButton) arg0.getSource();
        String day = jButton.getText();
        System.out.println(year + BAR + month + BAR + day);
        String bible = year + BAR + month + BAR + day;
        StringBuilder url = new StringBuilder();
        url.append(JSOUP_CRAWLING_GUI_API)
                .append(bible)
                .append(JSOUP_CRAWLING_URL_BIBLE_TYPE);

        try {
            Document document = Jsoup.connect(String.valueOf(url)).post();

            Element bible_text = printHashtagBibleInfo(document, BIBLE_TEXT);

            Element bibleinfo_box = printHashtagBibleInfo(document, HASHTAG_BIBLE_INFO_BOX);

            Element dailybible_info = printHashtagDailyBilbeInfo(document, HASHTAG_DAILY_BIBLE_INFO);

            area.append(dailybible_info.text() + "\n");
            area.append(bible_text.text() + "\n");
            area.append(bibleinfo_box.text() + "\n");

            Elements liList = document.select(BODY_LIST_LI);
            for (Element li : liList) {
                String line = li.select(CSS_INFO).first().text();
                if (line.length() > 65) { // 너무 길면 줄바꿈 하기
                    line = line.substring(0, 36) + "\n" + line.substring(36, 66) + "\n" + line.substring(66) + "\n";
                    area.append(li.select(CSS_NUM).first().text() + COLON + line);
                } else if (line.length() > 35) {
                    line = line.substring(0, 36) + "\n" + line.substring(36) + "\n";
                    area.append(li.select(CSS_NUM).first().text() + COLON + line);
                } else {
                    area.append(li.select(CSS_NUM).first().text() + COLON + li.select(CSS_INFO).first().text() + "\n");
                }
                System.out.print(li.select(CSS_NUM).first().text() + COLON);
                System.out.println(li.select(CSS_INFO).first().text());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element printHashtagDailyBilbeInfo(Document document, String hashtagDailyBibleInfo) {
        Element dailybible_info = document.select(hashtagDailyBibleInfo).first();
        System.out.println(dailybible_info.text());
        return dailybible_info;
    }

    private Element printHashtagBibleInfo(Document document, String hashtagBibleInfoBox) {
        Element bibleinfo_box = printHashtagDailyBilbeInfo(document, hashtagBibleInfoBox);
        return bibleinfo_box;
    }

    public void init() {
        select();
        calendar();
    }

    public void select() {
        JPanel panel = new JPanel(grid);//7행 7열의 그리드레이아웃

        for (int i = LAST_YEAR; i >= FIRST_YEAR; i--) {
            choiceYear.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) { //TODO : 1월 부터 12월 까지라서 바뀔 일이 없어 그대로 유지
            choiceMonth.add(String.valueOf(i));
        }
        for (int i = 0; i < day.length; i++) {//요일 이름을 레이블에 출력
            dayLabel[i] = new JLabel(day[i], JLabel.CENTER);
            panel.add(dayLabel[i]);
        }

        dayLabel[6].setForeground(Color.BLUE);//토요일 색상
        dayLabel[0].setForeground(Color.RED);//일요일 색상

        for (int i = 0; i < 42; i++) {//TODO : 달력은 42개의 날짜 고정이라 상수화 X
            days[i] = new JButton("");//제목이 없는 버튼 생성
            if (i % 7 == 0)
                days[i].setForeground(Color.RED);//일요일 버튼의 색
            else if (i % 7 == 6)
                days[i].setForeground(Color.BLUE);//토요일 버튼의 색
            else
                days[i].setForeground(Color.BLACK);
            days[i].addActionListener(this);
            panel.add(days[i]);
        }

        selectPanel.add(choiceYear);
        selectPanel.add(yearJLabel);
        selectPanel.add(choiceMonth);
        selectPanel.add(monthJLabel);
        
        area = new JTextArea(JTEXT_AREA_RWOS, JTEXT_AREA_COLUMNS);
        area.setCaretPosition(area.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(area);
        this.add(selectPanel, JSCROLLPANE_LOCATION_NORTH);//연도와 월을 선택할 수 있는 화면읠 상단에 출력
        this.add(panel, JSCROLLPANE_LOCATION_CENTER1);
        this.add(scrollPane, JSCROLLPANE_LOCATION_EAST);

        String m = (calendar.get(Calendar.MONTH) + 1) + BLANK;
        String y = calendar.get(Calendar.YEAR) + BLANK;
        choiceYear.select(y);
        choiceMonth.select(m);
        choiceYear.addItemListener(this);
        choiceMonth.addItemListener(this);
    }

    public void calendar() {
        year = Integer.parseInt(choiceYear.getSelectedItem());
        month = Integer.parseInt(choiceMonth.getSelectedItem());
        gregorianCalendar = new GregorianCalendar(year, month - 1, 1);
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
//		System.out.println("max+week:"+(max+week)+",week:"+week);
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {
        Color color = this.getBackground();
        if (arg0.getStateChange() == ItemEvent.SELECTED) {
            for (int i = 0; i < 42; i++) {//년이나 월이 선택되면 기존의 달력을 지우고 새로 그린다.
                if (!days[i].getText().equals(BLANK)) {
                    days[i].setText(BLANK);//기존의 날짜를 지움
                    days[i].setBackground(color);//달력의 배경색과 동일한 색으로 버튼의 배경색을 설정함.
                }
            }
            calendar();
        }
    }

//    public static JButton dateColor(JButton[] days){
//        for (int i = 0; i < 42; i++) {//년이나 월이 선택되면 기존의 달력을 지우고 새로 그린다.
//            if (!days[i].getText().equals("")) {
//                days[i].setText("");//기존의 날짜를 지움
//                days[i].setBackground(color);//달력의 배경색과 동일한 색으로 버튼의 배경색을 설정함.
//            }
//        }
//    }


}