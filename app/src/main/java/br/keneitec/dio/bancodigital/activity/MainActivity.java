package br.keneitec.dio.bancodigital.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.keneitec.dio.bancodigital.databinding.ActivityMainBinding;
import br.keneitec.dio.bancodigital.helper.Conta;
import br.keneitec.dio.bancodigital.helper.ContaCorrente;
import br.keneitec.dio.bancodigital.helper.ContaPoupanca;
import br.keneitec.dio.bancodigital.model.Cliente;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Conta cc;
    private Conta poupanca;
    private String extrato = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Cliente cliente = new Cliente();
        cliente.setNome("Venilton");
        Toast.makeText(this, "Bem vindo "+cliente.getNome(), Toast.LENGTH_SHORT).show();

        cc = new ContaCorrente(cliente);
        poupanca = new ContaPoupanca(cliente);

        cc.depositar(100);
        binding.tvSaldo.setText("Saldo: "+cc.getSaldo());

        binding.btnDepositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.etDeposit.getText().toString().isEmpty()){
                    depositar(new Double(binding.etDeposit.getText().toString()));
                }
            }
        });
        binding.btnSacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.etSacar.getText().toString().isEmpty()){
                    sacar(new Double(binding.etSacar.getText().toString()));
                }
            }
        });
        binding.btnTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.btnTransferir.getText().toString().isEmpty()){
                    transferir(new Double(binding.etTransferir.getText().toString()));
                }
            }
        });
    }

    private void transferir(Double v) {
        cc.transferir(v,poupanca);
        binding.tvSaldo.setText("Saldo: "+cc.getSaldo());
        extrato += "Transferencia de R$"+v+" para a poupança.\n";
        binding.textExtrato.setText(extrato);
        binding.etTransferir.getText().clear();
    }

    private void sacar(Double v) {
        Double saldoAnterior = cc.getSaldo();
        cc.sacar(v);
        binding.tvSaldo.setText("Saldo: "+cc.getSaldo());
        extrato += "Saque de R$"+v+". Saldo anterior: R$"+saldoAnterior+"\n";
        binding.textExtrato.setText(extrato);
        binding.etSacar.getText().clear();
    }

    private void depositar(Double v) {
        Double saldoAnterior = cc.getSaldo();
        cc.depositar(v);
        binding.tvSaldo.setText("Saldo: "+cc.getSaldo());
        extrato += "Deposito de R$"+v+". Saldo anterior: R$"+saldoAnterior+"\n";
        binding.textExtrato.setText(extrato);
        binding.etDeposit.getText().clear();
    }
}