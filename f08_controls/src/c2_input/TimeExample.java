package c2_input;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.scene.Parent;

public class TimeExample {

	public static void main(String[] args) {
		// time package의 Local 클래스는
		// 타임존 개념이 필요없는 날짜나 시간 정보를 나타내기 위해
		// 자바 1.8에서 새롭게 추가된 패키지 class 묶음
		// java.util.Date
		Date date = new Date();
		System.out.println(date);
		
		// 호출 되는 시점의 현재 시간
		LocalDate toDay = LocalDate.now();
		System.out.println(toDay);
		// 호출 되는 시점의 시간 정보
		LocalTime present = LocalTime.now();
		System.out.println(present);
		
		// of(년, 월, 일);
		LocalDate birthDay = LocalDate.of(1996, 07, 30);
		
		// of(시,분);
		// of(시,분,초);
		// of(시,분,초,나노타임);
		LocalTime birthTime = LocalTime.of(22, 22, 22);
		
		System.out.println(birthDay);
		System.out.println(birthTime);
		System.out.println(toDay.getYear());
		System.out.println(toDay.getMonthValue());
		System.out.println(toDay.getDayOfMonth());
		System.out.println(toDay.getDayOfWeek());
		System.out.println(toDay.getDayOfYear());
		
		System.out.println(present.getHour()+":"+present.getMinute()+":"+present.getSecond());
		// 1년전
		LocalDate target = toDay.minusYears(1);
		System.out.println(target);
		// 10년후
		target = toDay.plusYears(10);
		System.out.println(target);
	
		LocalDateTime targetDateTime = LocalDateTime.now();
		System.out.println(targetDateTime);
		targetDateTime = LocalDateTime.MAX;
		System.out.println(targetDateTime);
		targetDateTime = LocalDateTime.MIN;
		System.out.println(targetDateTime);
		
		// .of(LocalDate, LocalTime);
		targetDateTime = LocalDateTime.of(birthDay, birthTime);
		targetDateTime = LocalDateTime.of(2022,12,25,00,00,00);
		System.out.println(targetDateTime);
		targetDateTime = targetDateTime
				.withYear(2024)
				.withMonth(10)
				.withDayOfMonth(25)
				.withHour(13)
				.withMinute(30)
				.withSecond(20);
		System.out.println(targetDateTime);
	
		// Local 객체가 저장하고 있는 시간에 대한 정보를
		// 우리가 필요로 하는 형태의 문자열로 반환 - Patter 지정
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern(
				"yyyy년MM월dd일 E a HH시mm분ss초"
				);
		
		String now = targetDateTime.format(pattern);
		System.out.println(now);
		
		String strDate = "2023-12-25T12:00:10";
		
		LocalDateTime ldt = LocalDateTime.parse(strDate);
		System.out.println(ldt.getYear());
		
		ldt = LocalDateTime.parse(
				"1982-06-07 10:10:10",
				DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		System.out.println(ldt);
		now = ldt.format(pattern);
		System.out.println(now);
	}
	
}
