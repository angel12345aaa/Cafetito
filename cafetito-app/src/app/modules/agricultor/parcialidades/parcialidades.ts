import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-parcialidades',
  standalone: false,
  templateUrl: './parcialidades.html',
  styleUrls: ['./parcialidades.css']
})
export class Parcialidades implements OnInit {

  idPesaje!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    this.idPesaje = Number(
      this.route.snapshot.paramMap.get('idPesaje')
    );

    console.log('Pesaje seleccionado:', this.idPesaje);
  }

  regresar(): void {
    this.router.navigate(['/agricultor/pesajes']);
  }
}