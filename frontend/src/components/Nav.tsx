import { Link, NavLink } from 'react-router-dom';
import styled from 'styled-components';
// import './Nav.css';

const NavContainer = styled.div`
  display: flex;
  flex-direction: row;
  font-size: 20px;
  font-weight: 600;
  gap: 16px;
`;
const Line = styled.div`
  width: 2px;
  height: fill-available;
  background-color: var(--primary-100);
`;

// 현재 위치에 따라 내비게이션 아이템 색상 변경
const StyledLink = styled(NavLink)`
  text-decoration: none;
  color: gray;
  &.active {
    color: var(--primary-100);
  }
  &.pending {
    color: gray;
  }
`;

function Nav() {
  const current_path = window.location.pathname;

  return (
    <NavContainer>
      <StyledLink
        className={({ isActive, isPending }) =>
          isPending ? 'pending' : isActive ? 'active' : ''
        }
        to={'/'}
      >
        코드 에디터
      </StyledLink>
      <Line />
      <StyledLink
        className={({ isActive, isPending }) =>
          isPending ? 'pending' : isActive ? 'active' : ''
        }
        to={'/gitrepo'}
      >
        Git Repo
      </StyledLink>
    </NavContainer>
  );
}

export default Nav;
