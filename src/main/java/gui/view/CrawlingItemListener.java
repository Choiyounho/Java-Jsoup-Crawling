package gui.view;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static utils.CommonsConstant.BLANK;

public class CrawlingItemListener implements ItemListener {

    public JButton[] getDays() {
        return days;
    }

    private JButton[] days;

    public CrawlingItemListener(JButton[] days) {
        this.days = days;
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        CalendarView calendarView = new CalendarView();
        if (event.getStateChange() == ItemEvent.SELECTED) {
            for (int i = 0; i < 42; i++) {//년이나 월이 선택되면 기존의 달력을 지우고 새로 그린다.
                if (!days[i].getText().equals(BLANK)) {
                    days[i].setText(BLANK);//기존의 날짜를 지움
                    days[i].setBackground(calendarView.getBackgroundColor());//달력의 배경색과 동일한 색으로 버튼의 배경색을 설정함.
                }
            }
            calendarView.calendar();
        }
    }
}
