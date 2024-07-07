package com.example.doanfashionapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.doanfashionapp.ActivityThongTinSanPham;
import com.example.doanfashionapp.Adapter.SearchHistoryAdapter;
import com.example.doanfashionapp.DAO.DAO_HangSanPham;
import com.example.doanfashionapp.DAO.DAO_LoaiSanPham;
import com.example.doanfashionapp.DAO.DAO_SanPhamYeuThich;
import com.example.doanfashionapp.DTO.HangSanPham;
import com.example.doanfashionapp.Adapter.CustomListViewAdapter_Home_Bottom_Nav;
import com.example.doanfashionapp.DTO.LoaiSanPham;
import com.example.doanfashionapp.DTO.SanPham;
import com.example.doanfashionapp.Interface.SelectListener;
import com.example.doanfashionapp.R;
import com.example.doanfashionapp.SearchActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Bottom_NavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Bottom_NavFragment extends Fragment implements MenuProvider ,SelectListener, SearchHistoryAdapter.OnHistoryItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText edtSearch;
    private ImageButton btnFilter;
    private ListView listView;

    private ArrayList<LoaiSanPham> arrayList=new ArrayList<>();
    private CustomListViewAdapter_Home_Bottom_Nav adapter;

    private DAO_SanPhamYeuThich dao_sanPhamYeuThich;
    private PopupWindow popupWindow;
    private ListView listViewHistory;
    private String username;
    private SearchHistoryAdapter searchHistoryAdapter;
    public static String path = "/data/data/com.example.doanfashionapp/databases/FashionAppDB.db";
    public Home_Bottom_NavFragment() {
        // Required empty public constructor
    }

    public static Home_Bottom_NavFragment newInstance(String param1, String param2) {
        Home_Bottom_NavFragment fragment = new Home_Bottom_NavFragment();
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
        dao_sanPhamYeuThich=new DAO_SanPhamYeuThich(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home__bottom__nav, container, false);
        MenuHost menuHost= requireActivity();
        menuHost.addMenuProvider(this,getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        edtSearch=(EditText) view.findViewById(R.id.edtSearch);
        btnFilter=(ImageButton) view.findViewById(R.id.btnFilter);
        listView=(ListView) view.findViewById(R.id.listViewDSSP);
        DAO_LoaiSanPham dao_loaiSanPham = new DAO_LoaiSanPham(getContext());
        arrayList= dao_loaiSanPham.loadLoaiSP();
        adapter=new CustomListViewAdapter_Home_Bottom_Nav(getContext(),R.layout.custom_listview_home_bottom_nav,arrayList,Home_Bottom_NavFragment.this);

        listView.setAdapter(adapter);

        setupSearchEditText();
        return view;
    }


    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.option_menu,menu);

    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.Cart){
            FragmentManager fragmentManager = getParentFragmentManager();
            if (fragmentManager != null) {
                // Tạo một giao dịch fragment mới
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                // Kiểm tra xem fragment transaction có null không
                if (transaction != null) {
                    CartFragment cartFragment = new CartFragment();
                    transaction.replace(R.id.content_frame, cartFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                } else {
                    Toast.makeText(getContext(), "Transaction is null", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "FragmentManager is null", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }


    @Override
    public void onItemClicked(SanPham sp) {
        Intent intent = new Intent(getContext(), ActivityThongTinSanPham.class);
        intent.putExtra("maSP", sp.getIdSanPham());
        intent.putExtra("tenSP",sp.getTenSanPham());
        intent.putExtra("giaSP",sp.getGiaSanPham());
        intent.putExtra("loaiSP",sp.getIdLoaiSP());
        intent.putExtra("motaSP",sp.getMoTaSP());
        intent.putExtra("brandSP",sp.getIdBrand());
        intent.putExtra("anhSP", sp.getIdAnhSanPham());
        startActivity(intent);
    }

    @Override
    public void onItemFavoriteClicked(SanPham sp, boolean isFavorite) {
        if (isFavorite) {
            dao_sanPhamYeuThich.removeFromFavorites(sp.getIdSanPham());
            Toast.makeText(getContext(), "Đã xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            dao_sanPhamYeuThich.addToFavorites(sp.getIdSanPham());
            Toast.makeText(getContext(), "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
        }
    }
    private static final String PREFS_NAME = "SearchHistoryPrefs"; // đại diện cho tên của tập hợp các từ khóa tìm kiếm đã lưu.
    private static final String PREF_KEY_PREFIX = "SearchHistory_";//  được sử dụng để lưu trữ danh sách các từ khóa tìm kiếm trong SharedPreferences.
    private static final int MAX_HISTORY_SIZE = 4; // Giới hạn số lượng mục lịch sử tìm kiếm

    // Lưu lịch sử tìm kiếm
    private void saveSearchQuery(String query) {
        // lưu từ khóa tìm kiếm vào SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> searchHistory = sharedPreferences.getStringSet(PREF_KEY_PREFIX + username, new HashSet<>());
        ArrayList<String> searchHistoryList = new ArrayList<>(searchHistory);

        // Nếu lịch sử đã có mục này, xóa nó để đưa lên đầu danh sách
        searchHistoryList.remove(query);

        // Thêm mục mới vào đầu danh sách
        searchHistoryList.add(0, query);

        //Nếu số lượng mục trong lịch sử vượt quá giới hạn MAX_HISTORY_SIZE, nó sẽ loại bỏ mục cuối cùng.
        if (searchHistoryList.size() > MAX_HISTORY_SIZE) {
            searchHistoryList = new ArrayList<>(searchHistoryList.subList(0, MAX_HISTORY_SIZE));
        }

        // Chuyển đổi lại thành Set và lưu trữ
        searchHistory = new HashSet<>(searchHistoryList);
        editor.putStringSet("SearchHistory_" + username, searchHistory);
        editor.apply();
    }

    private void setupSearchEditText() {
        // để hiện popup khi EditText được focus, POPUP này chứa 1 listview hiển thị các danh sách từ khóa được kiếm trước đó
        // popup_search_history.xml
        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showSearchHistoryPopup();
                } else {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                }
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = edtSearch.getText().toString();
                    performSearch(query);
                    saveSearchQuery(query); // từ khóa tìm kiếm được lưu vào SharedPreferences thông qua phương thức saveSearchQuery(String query)
                    if (popupWindow != null) {
                        popupWindow.dismiss(); // // Ẩn popup sau khi tìm kiếm
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // Hiển thị lịch sử kiểu nổi lên Popup
    private void showSearchHistoryPopup() {
        View popupView = getLayoutInflater().inflate(R.layout.popup_search_history, null);
        listViewHistory = popupView.findViewById(R.id.listViewHistory);

        updateSearchHistory();

        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false); // Đặt focusable thành false để không chặn các sự kiện chạm khác

        // Đặt dismiss listener để đảm bảo popupWindow được đóng khi EditText mất focus
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                edtSearch.clearFocus();
            }
        });

        popupWindow.showAsDropDown(edtSearch);
    }

    // Hiển thị lịch sử tìm kiếm
    private void updateSearchHistory() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Set<String> searchHistorySet = sharedPreferences.getStringSet(PREF_KEY_PREFIX + username, new HashSet<>());
        ArrayList<String> searchHistory = new ArrayList<>(searchHistorySet);
        searchHistoryAdapter = new SearchHistoryAdapter(getContext(), searchHistory, (SearchHistoryAdapter.OnHistoryItemClickListener) this,username);
        listViewHistory.setAdapter(searchHistoryAdapter);

        if (searchHistory.isEmpty()) {
            listViewHistory.setVisibility(View.GONE);
        } else {
            listViewHistory.setVisibility(View.VISIBLE);
        }
    }

    // dùng lệnh sql để kiếm sản phẩm
    private void performSearch(String query) {
        SQLiteDatabase db = null;

        String queryNoDiacritics = ConvertString(query); // chuyển từ mình search thành ko dấu và ko viết hoa
        db=SQLiteDatabase.openOrCreateDatabase(path,null);
        String sql = "SELECT * FROM PRODUCTS";  // Lấy tất cả sản phẩm
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<SanPham> searchResults = new ArrayList<>(); // danh sách kết quả tìm kiếm
        while (cursor.moveToNext()) {
            String maSP = cursor.getString(0);
            String tenSP = cursor.getString(1);
            String loaiSP = cursor.getString(2);
            int giaSP = cursor.getInt(3);
            String bienTam = cursor.getString(4);
            String[] parts = bienTam.split(".jpg");
            String anhDaiDien = parts[0];
            int idAnh = getResources().getIdentifier(anhDaiDien, "drawable", requireContext().getPackageName());

            String moTa = cursor.getString(5);
            String maHang = cursor.getString(6);

            String tenSPNoDiacritics = ConvertString(tenSP); // chuyển tên sp trong csdl thành viết thường và ko dấu

            if (tenSPNoDiacritics.contains(queryNoDiacritics)) {    // Nếu tên sp sau khi đổi giống với từ mình kiếm thì ra kết quả
                SanPham sanPham = new SanPham(maSP, idAnh, tenSP, giaSP, moTa, maHang, loaiSP);
                searchResults.add(sanPham);
            }
        }
        cursor.close();

        if (searchResults.isEmpty()) {
            Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putParcelableArrayListExtra("searchResults",searchResults);
            startActivity(intent);
        }
    }
    // Chuyển tiếng việt có dấu thành ko dấu
    private char[] charA = { 'à', 'á', 'ạ', 'ả', 'ã',
            'â', 'ầ', 'ấ', 'ậ', 'ẩ', 'ẫ', 'ă', 'ằ', 'ắ', 'ặ', 'ẳ', 'ẵ' };
    private char[] charE = { 'ê', 'ề', 'ế', 'ệ', 'ể', 'ễ',
            'è', 'é', 'ẹ', 'ẻ', 'ẽ' };
    private char[] charI = { 'ì', 'í', 'ị', 'ỉ', 'ĩ' };
    private char[] charO = { 'ò', 'ó', 'ọ', 'ỏ', 'õ',
            'ô', 'ồ', 'ố', 'ộ', 'ổ', 'ỗ',
            'ơ', 'ờ', 'ớ', 'ợ', 'ở', 'ỡ' };
    private char[] charU = { 'ù', 'ú', 'ụ', 'ủ', 'ũ',
            'ư', 'ừ', 'ứ', 'ự', 'ử', 'ữ' };
    private char[] charY = { 'ỳ', 'ý', 'ỵ', 'ỷ', 'ỹ' };
    private char[] charD = { 'đ', ' ' };

    String charact = String.valueOf(charA) + String.valueOf(charE)
            + String.valueOf(charI) + String.valueOf(charO)
            + String.valueOf(charU) + String.valueOf(charY)
            + String.valueOf(charD);

    private char GetAlterChar(char pC) {
        if ((int) pC == 32) {
            return ' ';
        }

        char tam = pC;

        int i = 0;
        while (i < charact.length() && charact.charAt(i) != tam) {
            i++;
        }
        if (i < 0 || i > 67)
            return pC;

        if (i == 66) {
            return 'd';
        }
        if (i >= 0 && i <= 16) {
            return 'a';
        }
        if (i >= 17 && i <= 27) {
            return 'e';
        }
        if (i >= 28 && i <= 32) {
            return 'i';
        }
        if (i >= 33 && i <= 49) {
            return 'o';
        }
        if (i >= 50 && i <= 60) {
            return 'u';
        }
        if (i >= 61 && i <= 65) {
            return 'y';
        }
        return pC;
    }

    public String ConvertString(String pStr) {
        String convertString = pStr.toLowerCase();
        for (int i = 0; i < convertString.length(); i++) {
            char temp = convertString.charAt(i);
            if ((int) temp < 97 || temp > 122) {
                char tam1 = this.GetAlterChar(temp);
                if ((int) temp != 32)
                    convertString = convertString.replace(temp, tam1);
            }
        }
        return convertString;
    }
    // implement từ SearchHistoryAdapter
    // Khi người dùng chọn một từ khóa từ danh sách lịch sử tìm kiếm, sự kiện này được xử lý bởi phương thức onHistoryItemClick(String query).
    @Override
    public void onHistoryItemClick(String query) {
        // Đặt từ khóa vào EditText
        edtSearch.setText(query);
        // Thực hiện tìm kiếm với từ khóa này
        performSearch(query);
    }
}