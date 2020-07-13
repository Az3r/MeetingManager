package com.azer.meetingmanager.data.samples;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import com.azer.meetingmanager.data.models.Location;
import com.azer.meetingmanager.data.models.Meeting;

public class MeetingSamples {
    private static Random random = new Random();

    private static List<String> addresses = Arrays.asList(
            "Số 97 Đường 24, Phường Long Thạnh Mỹ, Quận 9, Thành phố Hồ Chí Minh",
            "Số L50/2 Đường Cư xá Phú Lâm A, Phường 12, Quận 6, Thành phố Hồ Chí Minh",
            "72/84 Huỳnh Văn Nghệ, Phường 15, Quận Tân Bình, Thành phố Hồ Chí Minh",
            "Số 54A Dương Quảng Hàm, Phường 5, Quận Gò Vấp, Thành phố Hồ Chí Minh", "2Bis, Nguyễn Thị Minh Khai",
            "35-45 Lê Thánh Tôn", "172 Nguyễn Duy Trinh, P. Bình Trưng", "Siêu thị An Phú, 43 Thảo Điền",
            "CoopMart, 168 Nguyễn Đình Chiểu", "387-389 Hai Bà Trưng", "223 Nguyễn Tri Phương");

    private static List<String> locationNames = Arrays.asList("7 Kỳ Quan - Dimsum & Món Hoa", "Sông Trăng - Bình Qưới",
            "Nhà Văn hóa Sinh viên ĐHQG", "Melisa Center - Trung Tâm Tiệc Cưới Hội Nghị",
            "Buffet Nướng - Khách Sạn Hương Sen", "Continental Palace Saigon Buffet", "Lotte Legend Saigon Hotel",
            "Gala Royale - Tiệc Cưới & Hội Nghị", "The Adora - Tiệc Cưới & Hội Nghị", "Callary - Nhà hàng Tiệc Cưới",
            "Vườn Cau - Nhà Hàng Tiệc Cưới");

    private static List<String> meetingNames = Arrays.asList(
      "Hội nghị Bộ trưởng GTVT các nước ASEAN lần thứ 25",
      "Hội nghị Ban Thường vụ Tổng hội Y học Việt Nam lần thứ 3 nhiệm kỳ XVI",
      "Hội nghị đại biểu cán bộ công chức Bộ Công Thương năm 2019",
      "Hội nghị tập huấn chuyên đề hướng dẫn các quy định về chương trình Giáo dục",
      "Hội nghị người lao động năm 2018",
      "Hội nghị Khách hàng VIP 2018 tiếp nối thành công",
      "Họp mặt kỷ niệm Ngày Dân số Việt Nam",
      "Họp mặt ban cán sự lớp",
      "Họp mặt ban cán sự hội sinh viên"
    );

    public static Location createLocation() {
        int x = random.nextInt(addresses.size());
        int y = random.nextInt(locationNames.size());
        Location location = new Location(locationNames.get(y), addresses.get(x), random.nextInt(201) + 100);
        System.out.println("created " + location.toString());
        return location;
    }

    public static List<Location> createLocation(int size) {
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            locations.add(createLocation());
        }
        return locations;
    }

    public static Meeting createMeeting() {
        String name = meetingNames.get(random.nextInt(meetingNames.size()));
        Location location = createLocation();

        String shortDesc = "short description";
        shortDesc = repeat(shortDesc, 100 / shortDesc.length());
        String detailDesc = "detail description";
        detailDesc = repeat(detailDesc, 1000 / detailDesc.length());

        int i = random.nextInt(10) + 1;
        InputStream istream = MeetingSamples.class.getClassLoader().getResourceAsStream("images/" + i + ".jpg");
        byte[] photo = new byte[0];

        try {
            photo = istream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date holdTime = new GregorianCalendar(2020, random.nextInt(12), random.nextInt(30) + 1, random.nextInt(24) + 1,
                random.nextInt(60)).getTime();
        int duration = random.nextInt(3600) + 7200;

        Meeting meeting = new Meeting(name, shortDesc, detailDesc, photo, holdTime, duration, location);
        System.out.println("created " + meeting.toString());
        return meeting;
    }

    public static List<Meeting> createMeeting(int size) {
        ArrayList<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            meetings.add(createMeeting());
        }
        return meetings;
    }

    private static String repeat(String value, int count) {
        return new String(new char[count]).replace("\0", value);
    }
}