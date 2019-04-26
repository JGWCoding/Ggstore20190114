package ggstore.com.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.view.MyNestedScrollView;

public class RegisterActivity extends BaseTitleActivity {

    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.register);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }

    String text = " \t   歡迎你使用Googoogaga.com.hk 的服務；Googoogaga 為閣下提供服務時受以下服務條款限制，本服務條款有可能不時被本公司更新而不作另行通知，你亦可隨時瀏覽本公司有關之服務條款的最新動向 www.googoogaga.com.hk。此外，Googoogaga 亦可能提供其他受不同或附加條款限制之服務。"
            + "<br/><br/>" + "在任何情況下，若有任何網路或接駁方面發生問題，包括但不限於錯誤、遺漏、服務中斷、延誤、失效及電腦病毒入侵等以致數據部份或全部失效，Googoogaga 均無須就相關的損失、損壞、責任債務和支出承擔任何責任。 "
            + "<br/><br/>" + "用戶須透過其服務上載、刊登、電子郵件發送、提供或以其他方式傳輸之內容承擔全部責任。 為保障 Googoogaga 其平台形象及質素，如使用者上載粗俗、淫穢或令人反感之內容，在任何情況下，Googoogaga 均有權刪除其內容，以免影響其他用戶。 "
            + "<br/><br/>" + "<B>資料保護</B>"
            + "<br/><br/>" + "為防止未獲授權登入，保持數據的安全及確保資料得到正確使用，我們會保護網站所收集的資料安全性；不容許傳輸任何有關非法性、威脅性、騷擾性或誹謗性等內容,以確保本網頁之其優質形象及服務質素。 "
            + "<br/><br/>" + "<B>個人資料收集聲明</B>"
            + "<br/><br/>" + "我們不會在沒有用戶的同意下向第三者披露你的個人資料，包括但不限於閣下的姓名、性別、年齡、身分證號碼、電話號碼、傳真號碼、住址、電子郵件地址、信用卡、銀行賬號、教育程度、職業或收入等資料。 "
            + "<br/><br/>" + "<B>1. 私隱政策的適用範圍</B>"
            + "<br/><br/>" + "(a) 本個人資料收集聲明內容涵蓋Googoogaga如何處理從Googoogaga.com.hk收集的個人資料及閣下使用我們的服務。個人資料包括你的名字、地址、電郵地址或電話號碼。 "
            + "<br/><br/>" + "(b) 本個人資料收集聲明不適用於並非由我們擁有或控制的公司政策，也不適用於並非由我們聘用或管理的人士。 "
            + "<br/><br/>" + "<B>2. 資料收集及使用</B>"
            + "<br/><br/>" + "(a) 我們在你登記Googoogaga帳戶、使用Googoogaga的某些服務、瀏覽Googoogaga的網頁或參加我們的推廣計劃時，均會收集你的個人資料。我們亦會從我們的商業夥伴取得你的個人資料。 "
            + "<br/><br/>" + " (b) 當你在Googoogaga登記時，我們會問及你的個人資料，如姓名、電郵地址、出生日期、性別、職業、行業及個人興趣。當你使用我們的某些服務時，我們可能會再問及你的電話號碼、地址及個人識別號碼等因此，你在Googoogaga登記，並登入使用我們的服務後，對我們來說你並不是匿名的。 "
            + "<br/><br/>" + "(c) 我們會自動從你的電腦及瀏覽器上，接收並紀錄資料，包括網路協定位址 (IP Address) 、Googoogaga的cookie中資料、及你要求讀取的網頁紀錄。 "
            + "<br/><br/>" + "(d) 如閣下 點擊本聲明末端的“同意”鍵，表示 閣下已同意本公司使用閣下的個人資料（包括閣下的姓名、電話號碼、傳真號碼、電郵位址、郵寄地址等）作為本公司溝通及推廣之用。 "
            + "<br/><br/>" + "(e) 我們會使用資料作以下一般用途：提供更適合你的廣告及網頁內容、為你提供你所要求的產品或服務、改善我們的服務及就特別或新推出的產品與你聯絡。 "
            + "<br/><br/>" + "<B>3. 資訊共享及披露</B>"
            + "<br/><br/>" + "(a) 除了我們的關聯公司，我們不會租用、出售、或披露你的個人資料予他人。"
            + "<br/><br/>" + "(b) 我們會在以下情況向其他公司或人士發送或披露你的個人資料： "
            + "<br/><br/>" + "(i) 我們取得你同意分享該等資料；"
            + "<br/><br/>" + "(ii) 我們需要就提供你要求的產品或服務，分享你的個人資料；"
            + "<br/><br/>" + "(iii) 我們需要發送你的個人資料予代表我們向你提供產品或服務的公司(除非你提出反對，否則該等公司沒有權將我們提供的資料用於超越協助我們的範圍)； "
            + "<br/><br/>" + "(iv) 我們回應傳票、法庭、命令或法律程序，或我們行使我們的權利或對申索作出抗辯； "
            + "<br/><br/>" + "(v) 我們收到按照個人資料(私隱)條例第58條下提出的要求； "
            + "<br/><br/>" + "(vi) 我們認為有必要分享有關資料以協助調查、預防或制止非法活動； "
            + "<br/><br/>" + "(vii) 我們懷疑詐騙，或任何人的人身安全受到威脅； "
            + "<br/><br/>" + "(viii) 我們懷疑你在我們的網站的行為違反我們的使用條款，或特定的產品或服務守則；或 "
            + "<br/><br/>" + "(ix) 如果我們被其他公司收購或與之合併，我們會將你的有關資料移交新的實體，並會受制於另一份私隱條款。在此情況下，我們會在資料移交前通知你。 "
            + "<br/><br/>" + "(x) 你使用我們的某些服務，如討論區及個人化網站，你的某些個人資料(如帳戶名稱)會被披露，並為他人所瀏覽。 "
            + "<br/><br/>" + "<B>4. Cookie</B>"
            + "<br/><br/>" + "(a) 我們可能會定並取用你的電腦cookie。 "
            + "<br/><br/>" + "(b) 我們會容許在我們某些網頁上擺放廣告的其他公司到你的電腦設定並取用cookies。其他公司將根據其自訂的私隱政策。廣告商或其他公司是不能提取我們的cookies。 "
            + "<br/><br/>" + "<B>5. 修改資料的權利</B>"
            + "<br/><br/>" + "你有權在任何時候修改你在Googoogaga網頁上的帳戶資料。 "
            + "<br/><br/>" + "<B>6. 帳戶安全</B>"
            + "<br/><br/>" + "你的帳戶資料受密碼保護，以保障你的私隱及帳戶安全。"
            + "<br/><br/>" + "<B>7. 個人資料收集政策的修訂</B>"
            + "<br/><br/>" + "(a) 我們可能不時修訂本個人資料收集政策。如果我們在使用個人資料的規定上作出大修改，我們會在網頁上張貼告示，通知你有關修訂。 "
            + "<br/><br/>" + "(b) 你同意於該修改後繼續使用我們的產品與服務構成你同意及接受受經修改的個人資料收集政策約束。 "
            + "<br/><br/>" + "<B>8. 查詢及更改個人資料</B>"
            + "<br/><br/>" + "你有權通過向本公司支付合理之手續費，要求查閱及更改本公司所持有關於你的資料。若你希望查核本公司是否持有閣下之個人資料，或欲查閱或更改你的個人資料，請電郵至pfwong@googoogaga.com.hk或以郵寄方式致香港九龍海濱道139-141號，海濱中心1808室，與黃小姐聯絡。 "
            + "<br/><br/>" + "本人同意接受以上條款並願意接受任何Googoogaga的溝通訊息包括Googoogaga的資訊及講座、培訓等服務及活動的最新消息與推廣。";

    @Override
    protected void initWidget() {
        super.initWidget();
        //todo I need update this font style
        TextView registerDetails = (TextView) findViewById(R.id.activity_register_details);
        registerDetails.setText(Html.fromHtml(text));
        TextView disagree = findViewById(R.id.activity_register_disagree);
        final TextView agree = findViewById(R.id.activity_register_agree);
        final TextView agreeDetail = findViewById(R.id.activity_register_agree_detail);
        MyNestedScrollView scroll = findViewById(R.id.activity_register_scroll);
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                View onlyChild = v.getChildAt(0);
                if (onlyChild.getHeight() <= scrollY + v.getHeight() + 10) {   // 如果满足就是到底部了
                    agreeDetail.setVisibility(View.VISIBLE);
                    agree.setVisibility(View.VISIBLE);
                }else{

                }
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, RegisterDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
