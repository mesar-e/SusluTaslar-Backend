SusluTaslar-Backend e-ticaret süreçlerini yönetmek için geliştirilmiş, bir RESTful API projesidir.

Proje Hakkında
Bu proje, bir e-ticaret platformunun backend altyapısını; kullanıcı yönetimi, ürün kataloğu, sipariş süreçleri ve güvenli kimlik doğrulama (JWT) mekanizmalarıyla uçtan uca simüle eder.

 Kullanılan Teknolojiler
- **Backend:** Java, Spring Boot
- **Güvenlik:** Spring Security, JWT (JSON Web Token)
- **Veritabanı:** PostgreSQL, Spring Data JPA (Hibernate)
- **Validasyon:** Jakarta Validation API
- **Yapılandırma:** Maven

Temel Özellikler
- **JWT Tabanlı Kimlik Doğrulama:** Stateless (durumsuz) mimari ile güvenli giriş/çıkış ve yetkilendirme.
- **Rol Tabanlı Erişim Kontrolü (RBAC):** Admin ve Müşteri rolleri ile yetki yönetimi.
- **Güvenli İşlemler:** Sipariş süreçlerinde stok yönetimi ve transactional işlemler.
- **IDOR Koruması:** Kullanıcıların sadece kendi siparişlerini ve profillerini yönetebildiği güvenli servis katmanı.
- **Hata Yönetimi:** Global exception handling ile temiz ve anlaşılır API hata mesajları.
