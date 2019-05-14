import { Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RegisterService} from './register.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  form: FormGroup;

  constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router) {

    this.form = this.fb.group({
         username: ['', [Validators.required, Validators.email]],
         password: ['', [Validators.required, Validators.minLength(10)]]
    });

  }

  register() {

    const val = this.form.value;

    if (val.username && val.password) {

       this.registerService.registerUser(val.username, val.password)
                           .subscribe(resp => {

                                        const username = resp.body.username;

                                        sessionStorage.setItem('username', username);
                                        console.log('Пользователь зарегистрирован');

                                        this.router.navigateByUrl('/security/login');

         });

    }
  }
}
