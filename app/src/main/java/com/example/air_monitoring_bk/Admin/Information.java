package com.example.air_monitoring_bk.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.air_monitoring_bk.Info.InfoItem;
import com.example.air_monitoring_bk.Info.InfoListviewAdapter;
import com.example.air_monitoring_bk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Information#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Information extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Information() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Information.
     */
    // TODO: Rename and change types and number of parameters
    public static Information newInstance(String param1, String param2) {
        Information fragment = new Information();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        InfoListviewAdapter adapter = new InfoListviewAdapter(this, getInfoItem());
        ListView listViewInfo = view.findViewById(R.id.ListViewInfo);
        listViewInfo.setAdapter(adapter);
        return view;
    }

    private List<InfoItem> getInfoItem() {
        List<InfoItem> ListInfo = new ArrayList<>();
        ListInfo.add(new InfoItem(
                R.drawable.bg_green_comer_16,
                R.drawable.green,
                "0-50: Tốt",
                "Chất lượng không khí tốt, không ảnh hưởng tới sức khỏe.",
                "- Tự do thực hiện các hoạt động ngoài trời."));

        ListInfo.add(new InfoItem(
                R.drawable.bg_yellow_comer_16,
                R.drawable.yellow,
                "51-100: Trung bình",
                "Chất lượng không khí ở mức chấp nhận được. Tuy " +
                        "nhiên, đối với những người nhạy cảm (người già, trẻ em, " +
                        "người mắc các bệnh hô hấp, tim mạch…) có thể chịu " +
                        "những tác động nhất định tới sức khỏe.",
                "- Tự do thực hiện các hoạt động ngoài trời.\n\n" +
                        "- Với nhóm người nhạy cảm: nên theo dõi các triệu chứng " +
                        "như ho hoặc khó thở, nhưng vẫn có thể hoạt động bên ngoài."));

        ListInfo.add(new InfoItem(
                R.drawable.bg_orange_comer_16,
                R.drawable.orange,
                "101-150: Kém",
                "Những người nhạy cảm gặp phải các vấn đề về sức khỏe, những người bình thường ít ảnh hưởng.",
                "- Những người thấy có triệu chứng đau mắt, ho hoặc đau " +
                        "họng… nên cân nhắc giảm các hoạt động ngoài trời.\n\n" +
                        "- Đối với học sinh, có thể hoạt động bên ngoài, nhưng nên " +
                        "giảm bớt việc tập thể dục kéo dài.\n\n" +
                        "- Với nhóm người nhạy cảm: nên giảm các hoạt động mạnh và giảm thời gian hoạt động ngoài trời.\n\n" +
                        "- Những người mắc bệnh hen suyễn có thể cần sử dụng thuốc thường xuyên hơn."));

        ListInfo.add(new InfoItem(
                R.drawable.bg_red_comer_16,
                R.drawable.red,
                "151-200: Xấu",
                "Những người bình thường bắt đầu có các ảnh hưởng tới " +
                        "sức khỏe, nhóm người nhạy cảm có thể gặp những vấn " +
                        "đề sức khỏe nghiêm trọng hơn.",
                "- Mọi người nên giảm các hoạt động mạnh khi ở ngoài trời, " +
                        "tránh tập thể dục kéo dài và nghỉ ngơi nhiều hơn trong nhà.\n\n" +
                        "- Với nhóm người nhạy cảm: nên ở trong nhà và giảm " +
                        "hoạt động mạnh. Nếu cần thiết phải ra ngoài, hãy đeo" +
                        "khẩu trang đạt tiêu chuẩn."));

        ListInfo.add(new InfoItem(
                R.drawable.bg_violet_comer_16,
                R.drawable.violet,
                "201-300: Rất xấu",
                "Cảnh báo hưởng tới sức khỏe: mọi người bị ảnh hưởng tới sức khỏe nghiêm trọng hơn.",
                "- Mọi người hạn chế tối đa các " +
                        "hoạt động ngoài trời và chuyển " +
                        "tất cả các hoạt động vào trong nhà. " +
                        "Nếu cần thiết phải ra ngoài, hãy " +
                        "đeo khẩu trang đạt tiêu chuẩn.\n\n" +
                        "- Với nhóm người nhạy cảm: nên ở trong nhà và giảm " +
                        "hoạt động mạnh. "));

        ListInfo.add(new InfoItem(
                R.drawable.bg_brown_comer_16,
                R.drawable.brown,
                "301-500: Nguy hại",
                "Cảnh báo hưởng tới sức khỏe: mọi người bị ảnh hưởng tới sức khỏe nghiêm trọng hơn.",
                "- Mọi người nên ở trong nhà, đóng cửa ra vào và cửa sổ. Nếu " +
                        "cần thiết phải ra ngoài, hãy đeo khẩu trang đạt tiêu chuẩn."));
        return ListInfo;
    }


}