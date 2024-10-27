package com.example.appbansach.apdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbansach.ProductDetails;
import com.example.appbansach.R;
import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;
    private GioHang gioHang; // Tham chiếu đến giỏ hàng
    private List<Integer> selectedItems; // List to track selected product IDs
    private List<Integer> selectedItemsQty;
    public CartAdapter(Context context, List<SanPham> sanPhamList, GioHang giohang) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.gioHang = giohang;
        this.selectedItems = new ArrayList<>(); // Initialize as an empty list
        this.selectedItemsQty = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_items, viewGroup, false);
        }

        ImageView imgAnhSanPham = view.findViewById(R.id.imgPCart);
        TextView txtTenSanPham = view.findViewById(R.id.txtNameCartItem);
        TextView txtGiaBan = view.findViewById(R.id.txtPriceCartItem);
        CheckBox checkBox = view.findViewById(R.id.checkBox4); // Ensure this CheckBox exists in your layout
        SanPham sanPham = sanPhamList.get(i);

        byte[] anh = sanPham.getAnhSanPham();
        Bitmap bitmap = getBitmapFromBytes(anh);
        imgAnhSanPham.setImageBitmap(bitmap);
        txtTenSanPham.setText(sanPham.getTenSanPham());
        txtGiaBan.setText(String.format("%s$", sanPham.getGiaBan()));
        // Set onClickListener cho toàn bộ item
        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetails.class);

            // Truyền dữ liệu qua Intent
            intent.putExtra("tenSanPham", sanPham.getTenSanPham());
            intent.putExtra("giaBan", sanPham.getGiaBan());
            intent.putExtra("anhSanPham", sanPham.getAnhSanPham()); // truyền mảng byte
            context.startActivity(intent);
        });
        // Xử lý sự kiện cho nút "+" và "-"
        Button btnPlus = view.findViewById(R.id.btnPlus);
        Button btnMinus = view.findViewById(R.id.btnMinus);
        TextView qty = view.findViewById(R.id.txtQtyCart);
        btnPlus.setOnClickListener(v -> {
            int sl= Integer.parseInt(qty.getText().toString());
            qty.setText(String.valueOf(sl+1));
            notifyDataSetChanged();
        });
// Set checkbox state
        checkBox.setChecked(selectedItems.contains(sanPham.getMaSanPham()));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Add to selected items if checked
                if (!selectedItems.contains(sanPham.getMaSanPham())) {
                    selectedItems.add(sanPham.getMaSanPham());
                    selectedItemsQty.add(Integer.parseInt(qty.getText().toString()));
                }
            } else {
                // Remove from selected items if unchecked
                selectedItems.remove(Integer.valueOf(sanPham.getMaSanPham()));
                selectedItemsQty.remove(Integer.parseInt(qty.getText().toString()));
            }

        });
        btnMinus.setOnClickListener(v -> {
            if (Integer.parseInt(qty.getText().toString()) > 1) { // Đảm bảo số lượng không giảm xuống dưới 1
                int sl= Integer.parseInt(qty.getText().toString());
                qty.setText(String.valueOf(sl-1));
                notifyDataSetChanged();
            } else {
                // Nếu số lượng là 1, có thể xóa sản phẩm khỏi giỏ hàng
                sanPhamList.remove(i);
                gioHang.removeFromCart(sanPham.getMaSanPham());
            }
            notifyDataSetChanged();
        });

        ImageView btnDelete = view.findViewById(R.id.imgDelete);
        btnDelete.setOnClickListener(v -> {
            // Xóa sản phẩm khỏi giỏ hàng
            gioHang.removeFromCart(sanPham.getMaSanPham());
            // Cập nhật danh sách sản phẩm trong adapter
            sanPhamList.remove(i);
            notifyDataSetChanged(); // Thông báo cho ListView cập nhật lại
        });
        return view;
    }

    // Chuyển Byte thành Bitmap
    public Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    // Method to get selected items
    public List<Integer> getSelectedItems() {
        return selectedItems;
    }
    public List<Integer> getSelectedItemsQty() {
        return selectedItemsQty;
    }
}
