# CLAUDE.md — Dự án Test Automation [Tên sản phẩm]

## Tổng quan
Repo này chứa toàn bộ mã nguồn kiểm thử tự động cho [tên sản phẩm]:
Page Object, Test class, test data, cấu hình chạy và report. Đây không phải
repo chứa code nguồn ứng dụng.

## Tech stack
- Ngôn ngữ: Java 17 — Build: Maven
- Thư viện: Selenium WebDriver, TestNG, Allure Report
- Mô hình: Page Object Model (POM), chạy parallel mặc định 5 luồng

## Quy ước đặt tên
- Page class: PascalCase + hậu tố `Page`, ví dụ `LoginPage.java`
- Test class: PascalCase + hậu tố `Test`, ví dụ `LoginTest.java`
- Test method: bắt đầu bằng `test` + mô tả hành vi, ví dụ `testLoginWithValidCredentials()`
- Biến locator: lowerCamelCase + mô tả element, ví dụ `loginButton`, `usernameInput`

## Kiến trúc bắt buộc
- Page class: chỉ khai báo locator và method tương tác UI
- Test class: chỉ chứa logic kiểm thử và assertion
- Test data tách riêng khỏi code (JSON / DataProvider / Utils)
- KHÔNG đặt assertion trong Page class

## Quy tắc viết locator
- Thứ tự ưu tiên: `id` > `data-testid` > `name` > `cssSelector` > `xpath`
- KHÔNG dùng xpath tuyệt đối bám theo cấu trúc DOM (`/html/body/div[3]/...`)
- KHÔNG chọn element dựa trên class dùng cho styling

## Quy tắc chờ (wait)
- KHÔNG dùng `Thread.sleep()` trong mọi trường hợp
- Luôn dùng `WebDriverWait` + `ExpectedConditions`, timeout khai báo tập trung
  trong `ConfigReader`, không hardcode rải rác trong Page class

## Khi hỗ trợ sinh test script
- Bám sát test case manual được cung cấp, không tự bịa thêm bước
- Mỗi test method bắt buộc có ít nhất 1 assertion mô tả rõ expected behavior
- Dữ liệu unique (email, username, mã KH) phải sinh động bằng timestamp/UUID,
  không hardcode — để chạy parallel không đụng nhau
- Không tự suy đoán kết quả mong đợi khi test case chưa nêu rõ — hỏi lại
  thay vì đoán

## Trước khi bàn giao code
- Xoá toàn bộ `System.out.println` sinh ra khi debug
- Xoá code bị comment và locator/biến không dùng tới
- Test đỏ khi chạy parallel là test sai — sửa test data, KHÔNG hạ số luồng để giấu

## Ngôn ngữ
Comment và tài liệu viết bằng tiếng Việt, tên biến/hàm và thuật ngữ kỹ thuật
giữ nguyên tiếng Anh (ví dụ: locator, wait, assertion).