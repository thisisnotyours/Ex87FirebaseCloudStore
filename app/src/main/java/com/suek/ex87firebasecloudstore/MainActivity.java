package com.suek.ex87firebasecloudstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    //TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tv= findViewById(R.id.tv);
    }

    //버튼을 누르면 Firestore DB 에 저장- Map Collection 을 통으로 저장
    public void clickSave(View view) {
        //저장할 데이터를 Map 으로 생성
        Map<String, Object> user= new HashMap<>();
        user.put("name", "sam");
        user.put("age", 20);   //object- 어떤 자료형을 다 쓸수있음 --> 여기서 int 는 Object 로써 Integer
        user.put("address", "seoul");

        //Firestore DB 객체 소환
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

        //"users"이라는 이름의 Collection(자식노드같은 개념) 참조
        CollectionReference userRef= firebaseFirestore.collection("users");  //하위폴더같은 느낌
        Task task = userRef.add(user);
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this, "Saved!!", Toast.LENGTH_SHORT).show();   //success
            }
        });
    }




    //버튼을 누르면 Firestore DB 에서 값을 get()메소드를 이용하여 DB값 읽기
    public void clickLoad(View view) {
        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        CollectionReference usersRef= firebaseFirestore.collection("users");

        Task<QuerySnapshot> task= usersRef.get();
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot querySnapshot= task.getResult();
                    //users 안에 여러개의 문서가 있으므로.. 반복문 돌림
                    for(QueryDocumentSnapshot snapshot : querySnapshot){
                        Map<String, Object> user= snapshot.getData();
                        String name= user.get("name").toString();   //== String name= (String)user.get("name");
                        long age= (long) user.get("age");
                        String address= (String)user.get("address");  //== String address= user.get("address").toString;

                        TextView tv= findViewById(R.id.tv);
                        tv.append(name+"\n"+age+"\n"+address+"\n============\n");
                    }
                }
            }
        });
    }
}
