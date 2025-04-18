module com.aysuyigit.yonetim_uygulamasi_javafx {

    // JavaFX'in temel bileşenlerini kullanmak için gerekli modüller
    // JavaFX kontrol bileşenlerini (Button, Label, TextField vb.) kullanabilmek için gereklidir.
    requires javafx.controls;
    // JavaFX FXML dosyalarını (FXML UI tasarımları) yükleyebilmek için gereklidir.
    requires javafx.fxml;
    // Lombok kütüphanesi, Java'da getter, setter, constructor gibi metotları otomatik oluşturur.
    // Lombok, derleme zamanı (compile-time) kullanıldığı için "static" olarak eklenmiştir.
    requires static lombok;
    // JDBC ile veritabanı bağlantısı kurabilmek için gerekli modül
    // Java'daki SQL işlemlerini (Connection, Statement, ResultSet vb.) gerçekleştirebilmek için gereklidir.
    requires java.sql;
    //requires javafx.web;
    requires org.apache.poi.ooxml;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires java.mail;



    // UI geliştirme için kullanılan harici kütüphaneler
    // ControlsFX, gelişmiş UI bileşenlerini (örn: Notifikasyonlar, Doğrulama Alanları) sağlar.
    requires org.controlsfx.controls;
    // FormsFX, formlar için gelişmiş bileşenler sunan bir kütüphanedir.
    requires com.dlsc.formsfx;
    // ValidatorFX, form doğrulama işlemleri için kullanılır.
    requires net.synedra.validatorfx;
    // İkon kütüphanesi, UI'de çeşitli ikonları kullanmaya olanak tanır.
    requires org.kordamp.ikonli.javafx;
    // BootstrapFX, Bootstrap benzeri CSS stillerini JavaFX'e entegre eder.
    requires org.kordamp.bootstrapfx.core;
    requires jdk.jdi;
    requires com.h2database;
    requires jBCrypt;
    //requires eu.hansolo.tilesfx;

    // Paket Erişimlerine İzin vermek
    // `opens` ifadesi, bir paketin runtime'da (çalışma zamanında) refleksiyon (reflection) kullanılarak erişilebilir olmasını sağlar.
    // Ana paket (Root package) açılıyor, böylece FXML dosyalarından erişilebilir.
    opens com.aysuyigit.yonetim_uygulamasi_javafx to javafx.fxml;

    // DTO (Data Transfer Object) paketinin içeriği, JavaFX bileşenleri ve Lombok tarafından erişilebilir olmalıdır.
    opens com.aysuyigit.yonetim_uygulamasi_javafx.dto to javafx.base, lombok;

    // Controller sınıfları FXML tarafından kullanılacağı için açılması gerekiyor.
    opens com.aysuyigit.yonetim_uygulamasi_javafx.controller to javafx.fxml;

    // DAO (Data Access Object) sınıfları, SQL bağlantısı kullandığı için açılıyor.
    opens com.aysuyigit.yonetim_uygulamasi_javafx.dao to java.sql;

    // Veritabanı bağlantısı sağlayan sınıfların da SQL modülüne açık olması gerekiyor.
    opens com.aysuyigit.yonetim_uygulamasi_javafx.database to java.sql;

    // DAO sınıflarını dışarıya açıyoruz. Böylece başka modüller veritabanı işlemlerini çağırabilir.
    exports com.aysuyigit.yonetim_uygulamasi_javafx.dao;

    // // Veritabanı bağlantı paketini dış dünyaya açıyoruz. Diğer modüller DB bağlantısını kullanabilir.
    exports com.aysuyigit.yonetim_uygulamasi_javafx.database;

    // Paket dışa aktarmak
    // `exports` ifadesi, paketin diğer modüller tarafından erişilebilir olmasını sağlar.
    // Ana paketi dış dünyaya açıyoruz. Diğer modüller bu paketin içeriğini kullanabilir.
    exports com.aysuyigit.yonetim_uygulamasi_javafx;


}



/*

    //requires com.h2database;
    //requires jbcrypt;
    //requires org.apache.poi.ooxml;
    //requires org.apache.pdfbox;
    //requires java.desktop;
    //requires java.mail;
    //requires eu.hansolo.tilesfx;


}*/


